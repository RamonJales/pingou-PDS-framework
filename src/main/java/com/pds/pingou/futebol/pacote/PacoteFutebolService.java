package com.pds.pingou.futebol.pacote;

import com.pds.pingou.futebol.assinatura.AssinaturaFutebol;
import com.pds.pingou.futebol.assinatura.AssinaturaFutebolRepository;
import com.pds.pingou.futebol.assinatura.MembroAssinatura;
import com.pds.pingou.futebol.assinatura.MembroAssinaturaRepository;
import com.pds.pingou.futebol.pacote.dto.ItemPacoteFutebolRequestDTO;
import com.pds.pingou.futebol.pacote.dto.ItemPacoteFutebolResponseDTO;
import com.pds.pingou.futebol.pacote.dto.PacoteFutebolRequestDTO;
import com.pds.pingou.futebol.pacote.dto.PacoteFutebolResponseDTO;
import com.pds.pingou.futebol.plano.PlanoFutebol;
import com.pds.pingou.futebol.plano.PlanoFutebolRepository;
import com.pds.pingou.futebol.produto.CamisaFutebol;
import com.pds.pingou.futebol.produto.CamisaFutebolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para operações de negócio relacionadas a pacotes de camisas de futebol.
 * 
 * Este serviço contém a lógica inteligente para geração de pacotes familiares,
 * onde cada membro recebe sua camisa no tamanho correto.
 */
@Service
public class PacoteFutebolService {

    @Autowired
    private PacoteFutebolRepository pacoteRepository;

    @Autowired
    private ItemPacoteFutebolRepository itemRepository;

    @Autowired
    private PlanoFutebolRepository planoRepository;

    @Autowired
    private CamisaFutebolRepository camisaRepository;

    @Autowired
    private AssinaturaFutebolRepository assinaturaRepository;

    @Autowired
    private MembroAssinaturaRepository membroRepository;

    /**
     * Lista todos os pacotes.
     */
    public List<PacoteFutebolResponseDTO> listarTodos() {
        return pacoteRepository.findAll().stream()
                .map(PacoteFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca pacote por ID.
     */
    public PacoteFutebolResponseDTO buscarPorId(Long id) {
        PacoteFutebol pacote = pacoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado: " + id));
        return PacoteFutebolMapper.toDTO(pacote);
    }

    /**
     * Lista pacotes por plano.
     */
    public List<PacoteFutebolResponseDTO> listarPorPlano(Long planoId) {
        return pacoteRepository.findByPlanoId(planoId).stream()
                .map(PacoteFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista pacotes por mês e ano.
     */
    public List<PacoteFutebolResponseDTO> listarPorPeriodo(Integer mes, Integer ano) {
        return pacoteRepository.findByMesAndAno(mes, ano).stream()
                .map(PacoteFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo pacote base (sem itens por membro ainda).
     */
    @Transactional
    public PacoteFutebolResponseDTO criar(PacoteFutebolRequestDTO dto) {
        PlanoFutebol plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + dto.getPlanoId()));

        PacoteFutebol pacote = PacoteFutebolMapper.toEntity(dto, plano);
        pacote = pacoteRepository.save(pacote);

        // Se foram enviados itens genéricos, adicionar
        if (dto.getItens() != null && !dto.getItens().isEmpty()) {
            for (ItemPacoteFutebolRequestDTO itemDto : dto.getItens()) {
                adicionarItemAoPacote(pacote, itemDto);
            }
            pacote = pacoteRepository.save(pacote);
        }

        return PacoteFutebolMapper.toDTO(pacote);
    }

    /**
     * Gera pacote personalizado para uma assinatura específica.
     * 
     * Este é o método CENTRAL da lógica de plano família:
     * Para cada membro da assinatura, cria um item com a mesma camisa
     * mas no TAMANHO ESPECÍFICO daquele membro.
     * 
     * Exemplo:
     * - Assinatura família com 4 membros
     * - Camisa: Flamengo Principal 2024/2025
     * - Resultado:
     *   - Item 1: Flamengo (G) para Pai
     *   - Item 2: Flamengo (M) para Mãe
     *   - Item 3: Flamengo (INF_12) para Filho 1
     *   - Item 4: Flamengo (INF_8) para Filho 2
     */
    @Transactional
    public PacoteFutebolResponseDTO gerarPacoteParaAssinatura(Long pacoteId, Long assinaturaId) {
        PacoteFutebol pacoteBase = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado: " + pacoteId));

        AssinaturaFutebol assinatura = assinaturaRepository.findById(assinaturaId)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada: " + assinaturaId));

        // Validar que a assinatura é do mesmo plano
        if (!assinatura.getPlano().getId().equals(pacoteBase.getPlano().getId())) {
            throw new RuntimeException("O pacote pertence a um plano diferente da assinatura");
        }

        // Validar que a assinatura está ativa
        if (!assinatura.isAtiva()) {
            throw new RuntimeException("A assinatura não está ativa");
        }

        // Para cada item do pacote base, criar versões para cada membro
        List<ItemPacoteFutebol> itensBase = new ArrayList<>(pacoteBase.getItems());
        
        for (ItemPacoteFutebol itemBase : itensBase) {
            CamisaFutebol camisa = itemBase.getCamisa();
            
            // Para cada membro ativo, criar item com seu tamanho
            for (MembroAssinatura membro : assinatura.getMembrosAtivos()) {
                ItemPacoteFutebol itemMembro = new ItemPacoteFutebol();
                itemMembro.setPacote(pacoteBase);
                itemMembro.setCamisa(camisa);
                itemMembro.setTamanho(membro.getTamanho()); // TAMANHO DO MEMBRO!
                itemMembro.setMembroDestino(membro);
                itemMembro.setQuantidade(1);
                
                // Aplicar preferências de personalização do membro
                if (pacoteBase.getPlano().getPersonalizacaoInclusa() && membro.temPreferenciaPersonalizacao()) {
                    itemMembro.setNomePersonalizado(membro.getJogadorFavorito());
                    itemMembro.setNumeroPersonalizado(membro.getNumeroFavorito());
                }
                
                itemMembro.setStatusItem("PENDENTE");
                itemRepository.save(itemMembro);
                pacoteBase.getItems().add(itemMembro);
            }
        }

        // Marcar edição limitada como vendida
        if (pacoteBase.getEdicaoLimitada()) {
            pacoteBase.incrementarVendas();
        }

        pacoteBase = pacoteRepository.save(pacoteBase);
        return PacoteFutebolMapper.toDTO(pacoteBase);
    }

    /**
     * Gera pacotes para todos os assinantes ativos de um plano.
     */
    @Transactional
    public List<PacoteFutebolResponseDTO> gerarPacotesParaTodosAssinantes(Long pacoteId) {
        PacoteFutebol pacoteBase = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado: " + pacoteId));

        List<AssinaturaFutebol> assinaturasAtivas = assinaturaRepository
                .findAssinaturasAtivasByPlano(pacoteBase.getPlano().getId());

        List<PacoteFutebolResponseDTO> pacotesGerados = new ArrayList<>();
        
        for (AssinaturaFutebol assinatura : assinaturasAtivas) {
            try {
                PacoteFutebolResponseDTO pacoteGerado = gerarPacoteParaAssinatura(pacoteId, assinatura.getId());
                pacotesGerados.add(pacoteGerado);
            } catch (Exception e) {
                // Log erro mas continua para próxima assinatura
                System.err.println("Erro ao gerar pacote para assinatura " + assinatura.getId() + ": " + e.getMessage());
            }
        }

        return pacotesGerados;
    }

    /**
     * Adiciona um item a um pacote.
     */
    @Transactional
    public ItemPacoteFutebolResponseDTO adicionarItem(Long pacoteId, ItemPacoteFutebolRequestDTO dto) {
        PacoteFutebol pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado: " + pacoteId));

        ItemPacoteFutebol item = adicionarItemAoPacote(pacote, dto);
        pacoteRepository.save(pacote);

        return PacoteFutebolMapper.toDTO(item);
    }

    /**
     * Método auxiliar para adicionar item ao pacote.
     */
    private ItemPacoteFutebol adicionarItemAoPacote(PacoteFutebol pacote, ItemPacoteFutebolRequestDTO dto) {
        CamisaFutebol camisa = camisaRepository.findById(dto.getCamisaId())
                .orElseThrow(() -> new RuntimeException("Camisa não encontrada: " + dto.getCamisaId()));

        MembroAssinatura membro = null;
        if (dto.getMembroId() != null) {
            membro = membroRepository.findById(dto.getMembroId())
                    .orElseThrow(() -> new RuntimeException("Membro não encontrado: " + dto.getMembroId()));
        }

        ItemPacoteFutebol item = PacoteFutebolMapper.toEntity(dto, pacote, camisa, membro);
        item = itemRepository.save(item);
        pacote.adicionarItem(item);

        return item;
    }

    /**
     * Remove um item do pacote.
     */
    @Transactional
    public void removerItem(Long pacoteId, Long itemId) {
        PacoteFutebol pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado: " + pacoteId));

        ItemPacoteFutebol item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado: " + itemId));

        pacote.removerItem(item);
        itemRepository.delete(item);
        pacoteRepository.save(pacote);
    }

    /**
     * Lista pacotes disponíveis para um usuário baseado em sua assinatura.
     */
    public List<PacoteFutebolResponseDTO> listarPacotesParaUsuario(String email) {
        AssinaturaFutebol assinatura = assinaturaRepository.findByUserEmail(email)
                .orElse(null);
        
        if (assinatura == null || !assinatura.isAtiva()) {
            return List.of();
        }

        return pacoteRepository.findPacotesAtivosByPlano(assinatura.getPlano().getId()).stream()
                .map(PacoteFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza um pacote.
     */
    @Transactional
    public PacoteFutebolResponseDTO atualizar(Long id, PacoteFutebolRequestDTO dto) {
        PacoteFutebol pacote = pacoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado: " + id));

        PacoteFutebolMapper.updateEntity(pacote, dto);
        pacote = pacoteRepository.save(pacote);

        return PacoteFutebolMapper.toDTO(pacote);
    }

    /**
     * Deleta um pacote.
     */
    @Transactional
    public void deletar(Long id) {
        if (!pacoteRepository.existsById(id)) {
            throw new RuntimeException("Pacote não encontrado: " + id);
        }
        pacoteRepository.deleteById(id);
    }

    /**
     * Atualiza status de um item (PENDENTE -> SEPARADO -> ENVIADO -> ENTREGUE).
     */
    @Transactional
    public ItemPacoteFutebolResponseDTO atualizarStatusItem(Long itemId, String novoStatus) {
        ItemPacoteFutebol item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado: " + itemId));

        switch (novoStatus.toUpperCase()) {
            case "SEPARADO":
                item.marcarComoSeparado();
                break;
            case "ENVIADO":
                item.marcarComoEnviado();
                break;
            case "ENTREGUE":
                item.marcarComoEntregue();
                break;
            default:
                throw new RuntimeException("Status inválido: " + novoStatus);
        }

        item = itemRepository.save(item);
        return PacoteFutebolMapper.toDTO(item);
    }

    /**
     * Lista edições limitadas disponíveis.
     */
    public List<PacoteFutebolResponseDTO> listarEdicoesLimitadasDisponiveis() {
        return pacoteRepository.findEdicoesLimitadasDisponiveis().stream()
                .map(PacoteFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }
}
