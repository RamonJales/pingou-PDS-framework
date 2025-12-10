package com.pds.pingou.hq.service;

import com.pds.pingou.framework.core.service.BasePackageService;
import com.pds.pingou.hq.entity.*;
import com.pds.pingou.hq.exception.CuradoriaException;
import com.pds.pingou.hq.repository.PacoteHQRepository;
import com.pds.pingou.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Serviço para operações com Pacotes de HQ
 * Estende BasePackageService do framework
 */
@Service
@Transactional
public class PacoteHQService extends BasePackageService<PacoteHQ, PacoteHQRepository> {

    @Autowired
    private PacoteHQRepository repository;

    @Autowired
    private CuradoriaService curadoriaService;

    @Autowired
    private QuadrinhoService quadrinhoService;

    @Override
    protected PacoteHQRepository getRepository() {
        return repository;
    }

    @Override
    protected RuntimeException createNotFoundException(Long id) {
        return new CuradoriaException("Pacote não encontrado com ID: " + id);
    }

    /**
     * Busca pacotes por plano
     */
    public List<PacoteHQ> findByPlano(PlanoHQ plano) {
        return repository.findByPlano(plano);
    }

    /**
     * Busca pacotes curados para um usuário
     */
    public List<PacoteHQ> findCuradosParaUsuario(Long userId) {
        return repository.findByCuradoParaUserId(userId);
    }

    /**
     * Busca pacote curado para usuário em período específico
     */
    public Optional<PacoteHQ> findCuradoParaUsuarioNoPeriodo(Long userId, Integer periodo, Integer ano) {
        return repository.findByCuradoParaUserIdAndPeriodoAndAno(userId, periodo, ano);
    }

    /**
     * Busca pacotes para entrega hoje
     */
    public List<PacoteHQ> findParaEntregaHoje() {
        return repository.findParaEntregaHoje(LocalDate.now());
    }

    /**
     * Busca pacotes atrasados
     */
    public List<PacoteHQ> findAtrasados() {
        return repository.findAtrasados(LocalDate.now());
    }

    /**
     * Cria um pacote curado personalizadamente para um usuário
     */
    public PacoteHQ criarPacoteCurado(User user, PlanoHQ plano, Integer periodo, Integer ano, String temaMes) {
        
        // Verifica se já existe pacote para este usuário neste período
        Optional<PacoteHQ> existente = findCuradoParaUsuarioNoPeriodo(user.getId(), periodo, ano);
        if (existente.isPresent()) {
            throw new CuradoriaException(
                String.format("Já existe pacote curado para o usuário no período %d/%d", periodo, ano)
            );
        }

        // Calcula quantidades baseadas no plano
        int qtdClassicas = plano.calcularQuantidadeClassicas();
        int qtdModernas = plano.calcularQuantidadeModernas();
        boolean incluirColecionador = Boolean.TRUE.equals(plano.getIncluiEdicoesColecionador());

        // Curadoria personalizada
        List<Quadrinho> quadrinhosSelecionados = curadoriaService.curarPacotePersonalizado(
            user, qtdClassicas, qtdModernas, incluirColecionador
        );

        // Verifica se conseguiu selecionar HQs suficientes
        if (quadrinhosSelecionados.size() < plano.getMaxProdutosPorPeriodo()) {
            throw new CuradoriaException(
                String.format("Não há HQs suficientes disponíveis. Esperado: %d, Encontrado: %d",
                    plano.getMaxProdutosPorPeriodo(), quadrinhosSelecionados.size())
            );
        }

        // Cria o pacote
        PacoteHQ pacote = new PacoteHQ();
        pacote.setNome(String.format("Pacote %s - %d/%d", user.getUsername(), periodo, ano));
        pacote.setDescricao(String.format("Pacote personalizado curado para %s", user.getUsername()));
        pacote.setPlano(plano);
        pacote.setPeriodo(periodo);
        pacote.setAno(ano);
        pacote.setDataEntrega(calcularDataEntrega(periodo, ano));
        pacote.setCuradoParaUserId(user.getId());
        pacote.setTemaMes(temaMes);
        
        // Adiciona os itens ao pacote
        for (Quadrinho quadrinho : quadrinhosSelecionados) {
            ItemPacoteHQ item = new ItemPacoteHQ(pacote, quadrinho, 1);
            
            // Define motivo da escolha baseado nas características
            String motivo = gerarMotivoEscolha(quadrinho);
            item.setMotivoEscolha(motivo);
            
            // Marca edições de colecionador como destaque
            if (Boolean.TRUE.equals(quadrinho.getEdicaoColecionador())) {
                item.marcarComoDestaque("Edição de Colecionador");
            }
            
            pacote.addItem(item);
            
            // Decrementa estoque
            quadrinhoService.decrementarEstoque(quadrinho.getId());
        }

        // Salva o pacote
        PacoteHQ salvo = repository.save(pacote);

        // Registra no histórico do usuário
        curadoriaService.registrarRecebimento(user, quadrinhosSelecionados, salvo.getId());

        return salvo;
    }

    /**
     * Calcula a data de entrega baseada no período e ano
     */
    private LocalDate calcularDataEntrega(Integer periodo, Integer ano) {
        // Entrega no dia 15 do mês
        return LocalDate.of(ano, periodo, 15);
    }

    /**
     * Gera motivo da escolha do quadrinho
     */
    private String gerarMotivoEscolha(Quadrinho quadrinho) {
        StringBuilder motivo = new StringBuilder();
        
        if (Boolean.TRUE.equals(quadrinho.getEdicaoColecionador())) {
            motivo.append("Edição de Colecionador - ");
        }
        
        motivo.append(quadrinho.getTipoHQ().getNome())
              .append(" - ")
              .append(quadrinho.getCategoria().getDescricao())
              .append(" - ")
              .append(quadrinho.getEditora().getDescricao());
        
        return motivo.toString();
    }

    /**
     * Valida pacote antes de salvar
     */
    @Override
    protected void beforeSave(PacoteHQ pacote) {
        super.beforeSave(pacote);
        
        if (pacote.getPlano() == null) {
            throw new CuradoriaException("Pacote deve estar associado a um plano");
        }
        
        pacote.recalcularEstatisticas();
    }
}
