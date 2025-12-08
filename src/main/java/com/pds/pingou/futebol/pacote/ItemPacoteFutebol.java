package com.pds.pingou.futebol.pacote;

import com.pds.pingou.framework.core.entity.BasePackageItem;
import com.pds.pingou.futebol.assinatura.MembroAssinatura;
import com.pds.pingou.futebol.enums.TamanhoCamisa;
import com.pds.pingou.futebol.produto.CamisaFutebol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa um item específico dentro de um pacote de futebol.
 * 
 * A grande diferença aqui é que cada item tem seu TAMANHO ESPECÍFICO,
 * permitindo que um mesmo pacote contenha camisas de tamanhos diferentes
 * para atender planos família onde cada membro tem seu tamanho.
 * 
 * Exemplo:
 * - Pacote "Libertadores Dezembro 2024" para família de 4 pessoas:
 *   - Item 1: Camisa Flamengo G (para o pai)
 *   - Item 2: Camisa Flamengo M (para a mãe)
 *   - Item 3: Camisa Flamengo INF_12 (para o filho mais velho)
 *   - Item 4: Camisa Flamengo INF_8 (para o filho mais novo)
 * 
 * @author Pingou Team
 * @version 1.0
 */
@Entity
@Table(name = "item_pacote_futebol")
@Getter
@Setter
public class ItemPacoteFutebol extends BasePackageItem<PacoteFutebol, CamisaFutebol> {

    /** Pacote ao qual este item pertence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pacote_id", nullable = false)
    private PacoteFutebol pacote;

    /** Camisa incluída neste item */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camisa_id", nullable = false)
    private CamisaFutebol camisa;

    /** TAMANHO ESPECÍFICO desta camisa neste item - chave para planos família */
    @Enumerated(EnumType.STRING)
    @Column(name = "tamanho", nullable = false)
    private TamanhoCamisa tamanho;

    /** Membro da família para quem esta camisa é destinada (opcional) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membro_id")
    private MembroAssinatura membroDestino;

    /** Nome personalizado para estampar (se aplicável) */
    @Column(name = "nome_personalizado")
    private String nomePersonalizado;

    /** Número personalizado para estampar (se aplicável) */
    @Column(name = "numero_personalizado")
    private Integer numeroPersonalizado;

    /** Se é versão jogador (mais justa) - sobrescreve configuração da camisa */
    @Column(name = "versao_jogador")
    private Boolean versaoJogador;

    /** Status do item (PENDENTE, SEPARADO, ENVIADO, ENTREGUE) */
    @Column(name = "status_item")
    private String statusItem = "PENDENTE";

    public ItemPacoteFutebol() {}

    /**
     * Construtor básico para criação de um item de pacote.
     */
    public ItemPacoteFutebol(PacoteFutebol pacote, CamisaFutebol camisa, TamanhoCamisa tamanho) {
        this.pacote = pacote;
        this.camisa = camisa;
        this.tamanho = tamanho;
        setQuantidade(1);
    }

    /**
     * Construtor completo com membro destino para planos família.
     */
    public ItemPacoteFutebol(PacoteFutebol pacote, CamisaFutebol camisa, TamanhoCamisa tamanho,
                             MembroAssinatura membroDestino) {
        this(pacote, camisa, tamanho);
        this.membroDestino = membroDestino;
    }

    /**
     * Construtor com personalização.
     */
    public ItemPacoteFutebol(PacoteFutebol pacote, CamisaFutebol camisa, TamanhoCamisa tamanho,
                             String nomePersonalizado, Integer numeroPersonalizado) {
        this(pacote, camisa, tamanho);
        this.nomePersonalizado = nomePersonalizado;
        this.numeroPersonalizado = numeroPersonalizado;
    }

    @Override
    public PacoteFutebol getPackage() {
        return pacote;
    }

    @Override
    public void setPackage(PacoteFutebol pkg) {
        this.pacote = pkg;
    }

    @Override
    public CamisaFutebol getProduct() {
        return camisa;
    }

    @Override
    public void setProduct(CamisaFutebol product) {
        this.camisa = product;
    }

    /**
     * Verifica se o item tem personalização.
     */
    public boolean temPersonalizacao() {
        return (nomePersonalizado != null && !nomePersonalizado.isBlank()) 
            || (numeroPersonalizado != null && numeroPersonalizado > 0);
    }

    /**
     * Verifica se é camisa infantil.
     */
    public boolean isInfantil() {
        return tamanho != null && tamanho.isInfantil();
    }

    /**
     * Verifica se é camisa adulta.
     */
    public boolean isAdulto() {
        return tamanho != null && tamanho.isAdulto();
    }

    /**
     * Retorna descrição formatada para etiqueta de envio.
     */
    public String getDescricaoParaEtiqueta() {
        StringBuilder sb = new StringBuilder();
        sb.append(camisa.getTime()).append(" - ").append(camisa.getTipoCamisa().getNome());
        sb.append("\nTamanho: ").append(tamanho.getSigla());
        
        if (membroDestino != null) {
            sb.append("\nPara: ").append(membroDestino.getNome());
        }
        
        if (temPersonalizacao()) {
            sb.append("\nPersonalização: ");
            if (nomePersonalizado != null) {
                sb.append(nomePersonalizado);
            }
            if (numeroPersonalizado != null) {
                sb.append(" #").append(numeroPersonalizado);
            }
        }
        
        return sb.toString();
    }

    /**
     * Retorna a descrição curta do item.
     */
    public String getDescricaoCurta() {
        return String.format("%s (%s) - %s", 
            camisa.getTime(), 
            tamanho.getSigla(),
            membroDestino != null ? membroDestino.getNome() : "Sem destino definido"
        );
    }

    /**
     * Atualiza o status do item.
     */
    public void marcarComoSeparado() {
        this.statusItem = "SEPARADO";
    }

    public void marcarComoEnviado() {
        this.statusItem = "ENVIADO";
    }

    public void marcarComoEntregue() {
        this.statusItem = "ENTREGUE";
    }
}
