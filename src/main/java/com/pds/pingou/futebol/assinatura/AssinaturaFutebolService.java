package com.pds.pingou.futebol.assinatura;

import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.futebol.assinatura.dto.AssinaturaFutebolRequestDTO;
import com.pds.pingou.futebol.assinatura.dto.AssinaturaFutebolResponseDTO;
import com.pds.pingou.futebol.assinatura.dto.MembroAssinaturaRequestDTO;
import com.pds.pingou.futebol.assinatura.dto.MembroAssinaturaResponseDTO;
import com.pds.pingou.futebol.enums.TamanhoCamisa;
import com.pds.pingou.futebol.plano.PlanoFutebol;
import com.pds.pingou.futebol.plano.PlanoFutebolRepository;
import com.pds.pingou.security.user.User;
import com.pds.pingou.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para operações de negócio relacionadas a assinaturas de futebol.
 * 
 * Este serviço contém a lógica inteligente para gerenciar assinaturas familiares,
 * incluindo a gestão de membros com tamanhos diferentes.
 */
@Service
public class AssinaturaFutebolService {

    @Autowired
    private AssinaturaFutebolRepository assinaturaRepository;

    @Autowired
    private MembroAssinaturaRepository membroRepository;

    @Autowired
    private PlanoFutebolRepository planoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Busca assinatura por ID.
     */
    public AssinaturaFutebolResponseDTO buscarPorId(Long id) {
        AssinaturaFutebol assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada: " + id));
        return AssinaturaFutebolMapper.toDTO(assinatura);
    }

    /**
     * Busca assinatura por email do usuário.
     */
    public AssinaturaFutebolResponseDTO buscarPorEmail(String email) {
        AssinaturaFutebol assinatura = assinaturaRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada para o email: " + email));
        return AssinaturaFutebolMapper.toDTO(assinatura);
    }

    /**
     * Lista todas as assinaturas ativas.
     */
    public List<AssinaturaFutebolResponseDTO> listarAtivas() {
        return assinaturaRepository.findByStatus(StatusAssinatura.ATIVA).stream()
                .map(AssinaturaFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova assinatura de futebol com membros.
     * 
     * Este é o ponto central da lógica de plano família - cada membro
     * pode ter seu próprio tamanho de camisa.
     */
    @Transactional
    public AssinaturaFutebolResponseDTO criar(String userEmail, AssinaturaFutebolRequestDTO dto) {
        // Buscar usuário
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userEmail));

        // Verificar se já tem assinatura
        if (assinaturaRepository.existsByUser(user)) {
            throw new RuntimeException("Usuário já possui uma assinatura ativa");
        }

        // Buscar plano
        PlanoFutebol plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + dto.getPlanoId()));

        // Validar quantidade de membros
        int quantidadeMembros = dto.getMembros() != null ? dto.getMembros().size() : 0;
        if (quantidadeMembros == 0) {
            throw new RuntimeException("É necessário pelo menos um membro na assinatura");
        }
        if (quantidadeMembros > plano.getMaxMembros()) {
            throw new RuntimeException("O plano " + plano.getNome() + " permite no máximo " + 
                    plano.getMaxMembros() + " membros. Você informou " + quantidadeMembros);
        }

        // Validar que há pelo menos um titular
        boolean temTitular = dto.getMembros().stream().anyMatch(m -> m.getTitular() != null && m.getTitular());
        if (!temTitular) {
            // Define o primeiro como titular automaticamente
            dto.getMembros().get(0).setTitular(true);
        }

        // Criar assinatura
        AssinaturaFutebol assinatura = new AssinaturaFutebol();
        assinatura.setUser(user);
        assinatura.setPlano(plano);
        assinatura.setStatus(StatusAssinatura.ATIVA);
        assinatura.setDataInicio(LocalDate.now());
        assinatura.setDataExpiracao(LocalDate.now().plusMonths(1)); // Primeiro mês
        assinatura.setTimeFavoritoPrincipal(dto.getTimeFavoritoPrincipal());
        assinatura.setTimesSecundarios(dto.getTimesSecundarios());
        assinatura.setAceitaTimesRivais(dto.getAceitaTimesRivais() != null ? dto.getAceitaTimesRivais() : false);
        assinatura.setSelecoesPreferidas(dto.getSelecoesPreferidas());
        assinatura.setEnderecoEntrega(dto.getEnderecoEntrega());
        assinatura.setObservacoes(dto.getObservacoes());
        assinatura.setTrocasTamanhoUsadas(0);

        assinatura = assinaturaRepository.save(assinatura);

        // Adicionar membros com seus tamanhos específicos
        int ordem = 1;
        for (MembroAssinaturaRequestDTO membroDto : dto.getMembros()) {
            MembroAssinatura membro = AssinaturaFutebolMapper.toEntity(membroDto, assinatura);
            membro.setOrdem(ordem++);
            membroRepository.save(membro);
            assinatura.getMembros().add(membro);
        }

        return AssinaturaFutebolMapper.toDTO(assinatura);
    }

    /**
     * Adiciona um novo membro à assinatura (para upgrade de plano família).
     */
    @Transactional
    public MembroAssinaturaResponseDTO adicionarMembro(Long assinaturaId, MembroAssinaturaRequestDTO dto) {
        AssinaturaFutebol assinatura = assinaturaRepository.findById(assinaturaId)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada: " + assinaturaId));

        // Validar se pode adicionar mais membros
        if (!assinatura.podAdicionarMembro()) {
            throw new RuntimeException("Limite de membros atingido. O plano " + 
                    assinatura.getPlano().getNome() + " permite no máximo " + 
                    assinatura.getPlano().getMaxMembros() + " membros");
        }

        MembroAssinatura membro = AssinaturaFutebolMapper.toEntity(dto, assinatura);
        assinatura.adicionarMembro(membro);
        membro = membroRepository.save(membro);

        return AssinaturaFutebolMapper.toDTO(membro);
    }

    /**
     * Atualiza o tamanho de um membro (troca de tamanho).
     */
    @Transactional
    public MembroAssinaturaResponseDTO atualizarTamanhoMembro(Long membroId, TamanhoCamisa novoTamanho) {
        MembroAssinatura membro = membroRepository.findById(membroId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado: " + membroId));

        AssinaturaFutebol assinatura = membro.getAssinatura();

        // Verificar se ainda pode trocar tamanho
        if (!assinatura.podeTrocarTamanho()) {
            throw new RuntimeException("Limite de trocas de tamanho atingido para este ano. " +
                    "Disponível: " + assinatura.getPlano().getTrocasTamanhoAno() + 
                    ", Usado: " + assinatura.getTrocasTamanhoUsadas());
        }

        // Registrar troca
        TamanhoCamisa tamanhoAntigo = membro.getTamanho();
        membro.setTamanho(novoTamanho);
        assinatura.registrarTrocaTamanho();

        membroRepository.save(membro);
        assinaturaRepository.save(assinatura);

        return AssinaturaFutebolMapper.toDTO(membro);
    }

    /**
     * Atualiza informações de um membro.
     */
    @Transactional
    public MembroAssinaturaResponseDTO atualizarMembro(Long membroId, MembroAssinaturaRequestDTO dto) {
        MembroAssinatura membro = membroRepository.findById(membroId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado: " + membroId));

        // Se está mudando tamanho, verificar limite de trocas
        if (!membro.getTamanho().equals(dto.getTamanho())) {
            return atualizarTamanhoMembro(membroId, dto.getTamanho());
        }

        AssinaturaFutebolMapper.updateMembro(membro, dto);
        membro = membroRepository.save(membro);

        return AssinaturaFutebolMapper.toDTO(membro);
    }

    /**
     * Remove um membro da assinatura.
     */
    @Transactional
    public void removerMembro(Long membroId) {
        MembroAssinatura membro = membroRepository.findById(membroId)
                .orElseThrow(() -> new RuntimeException("Membro não encontrado: " + membroId));

        if (membro.getTitular()) {
            throw new RuntimeException("Não é possível remover o titular da assinatura");
        }

        AssinaturaFutebol assinatura = membro.getAssinatura();
        assinatura.removerMembro(membro);
        membroRepository.delete(membro);
    }

    /**
     * Lista membros de uma assinatura.
     */
    public List<MembroAssinaturaResponseDTO> listarMembros(Long assinaturaId) {
        return membroRepository.findMembrosAtivosByAssinatura(assinaturaId).stream()
                .map(AssinaturaFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retorna os tamanhos únicos necessários para uma assinatura.
     * Útil para a geração de pacotes.
     */
    public List<TamanhoCamisa> getTamanhosNecessarios(Long assinaturaId) {
        AssinaturaFutebol assinatura = assinaturaRepository.findById(assinaturaId)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada: " + assinaturaId));
        return assinatura.getTamanhosNecessarios();
    }

    /**
     * Suspende uma assinatura.
     */
    @Transactional
    public void suspender(Long id) {
        AssinaturaFutebol assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada: " + id));
        assinatura.suspender();
        assinaturaRepository.save(assinatura);
    }

    /**
     * Reativa uma assinatura.
     */
    @Transactional
    public void reativar(Long id) {
        AssinaturaFutebol assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada: " + id));
        assinatura.reativar();
        assinaturaRepository.save(assinatura);
    }

    /**
     * Cancela uma assinatura.
     */
    @Transactional
    public void cancelar(Long id) {
        AssinaturaFutebol assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada: " + id));
        assinatura.cancelar();
        assinaturaRepository.save(assinatura);
    }

    /**
     * Renova uma assinatura (reseta contador de trocas).
     */
    @Transactional
    public AssinaturaFutebolResponseDTO renovar(Long id) {
        AssinaturaFutebol assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada: " + id));
        
        assinatura.setDataExpiracao(assinatura.getDataExpiracao().plusMonths(1));
        assinatura.resetarTrocasTamanho();
        assinatura.setStatus(StatusAssinatura.ATIVA);
        
        assinatura = assinaturaRepository.save(assinatura);
        return AssinaturaFutebolMapper.toDTO(assinatura);
    }
}
