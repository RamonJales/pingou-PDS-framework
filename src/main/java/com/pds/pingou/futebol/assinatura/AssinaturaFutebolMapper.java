package com.pds.pingou.futebol.assinatura;

import com.pds.pingou.futebol.assinatura.dto.AssinaturaFutebolResponseDTO;
import com.pds.pingou.futebol.assinatura.dto.MembroAssinaturaRequestDTO;
import com.pds.pingou.futebol.assinatura.dto.MembroAssinaturaResponseDTO;

import java.util.stream.Collectors;

/**
 * Mapper para conversão entre entidades de assinatura e seus DTOs.
 */
public class AssinaturaFutebolMapper {

    private AssinaturaFutebolMapper() {
        // Utility class
    }

    /**
     * Converte DTO de membro request para entidade.
     */
    public static MembroAssinatura toEntity(MembroAssinaturaRequestDTO dto, AssinaturaFutebol assinatura) {
        MembroAssinatura membro = new MembroAssinatura();
        membro.setAssinatura(assinatura);
        membro.setNome(dto.getNome());
        membro.setTamanho(dto.getTamanho());
        membro.setTimeFavorito(dto.getTimeFavorito());
        membro.setJogadorFavorito(dto.getJogadorFavorito());
        membro.setNumeroFavorito(dto.getNumeroFavorito());
        membro.setTitular(dto.getTitular() != null ? dto.getTitular() : false);
        membro.setEmail(dto.getEmail());
        membro.setAtivo(true);
        return membro;
    }

    /**
     * Converte entidade de membro para DTO de response.
     */
    public static MembroAssinaturaResponseDTO toDTO(MembroAssinatura membro) {
        MembroAssinaturaResponseDTO dto = new MembroAssinaturaResponseDTO();
        dto.setId(membro.getId());
        dto.setNome(membro.getNome());
        dto.setTamanho(membro.getTamanho());
        dto.setTamanhoDescricao(membro.getTamanho().getDescricao());
        dto.setTamanhosigla(membro.getTamanho().getSigla());
        dto.setInfantil(membro.getTamanho().isInfantil());
        dto.setTimeFavorito(membro.getTimeFavorito());
        dto.setJogadorFavorito(membro.getJogadorFavorito());
        dto.setNumeroFavorito(membro.getNumeroFavorito());
        dto.setTitular(membro.getTitular());
        dto.setOrdem(membro.getOrdem());
        dto.setAtivo(membro.getAtivo());
        dto.setEmail(membro.getEmail());
        dto.setDescricaoParaEnvio(membro.getDescricaoParaEnvio());
        return dto;
    }

    /**
     * Converte entidade de assinatura para DTO de response.
     */
    public static AssinaturaFutebolResponseDTO toDTO(AssinaturaFutebol assinatura) {
        AssinaturaFutebolResponseDTO dto = new AssinaturaFutebolResponseDTO();
        dto.setId(assinatura.getId());
        dto.setUserId(assinatura.getUser().getId());
        dto.setUserEmail(assinatura.getUser().getEmail());
        dto.setUserName(assinatura.getUser().getNome());
        dto.setPlanoId(assinatura.getPlano().getId());
        dto.setPlanoNome(assinatura.getPlano().getNome());
        dto.setPlanoPreco(assinatura.getPlano().getPrecoFinalCalculado());
        dto.setStatus(assinatura.getStatus());
        dto.setStatusDescricao(assinatura.getStatus().name());
        dto.setDataInicio(assinatura.getDataInicio());
        dto.setDataExpiracao(assinatura.getDataExpiracao());
        dto.setMembros(assinatura.getMembros().stream()
            .map(AssinaturaFutebolMapper::toDTO)
            .collect(Collectors.toList()));
        dto.setQuantidadeMembrosAtivos(assinatura.getQuantidadeMembrosAtivos());
        dto.setMaxMembros(assinatura.getPlano().getMaxMembros());
        dto.setPodeAdicionarMembro(assinatura.podAdicionarMembro());
        dto.setTimeFavoritoPrincipal(assinatura.getTimeFavoritoPrincipal());
        dto.setTimesSecundarios(assinatura.getTimesSecundarios());
        dto.setAceitaTimesRivais(assinatura.getAceitaTimesRivais());
        dto.setSelecoesPreferidas(assinatura.getSelecoesPreferidas());
        dto.setTrocasTamanhoUsadas(assinatura.getTrocasTamanhoUsadas());
        dto.setTrocasTamanhoDisponiveis(assinatura.getPlano().getTrocasTamanhoAno() - assinatura.getTrocasTamanhoUsadas());
        dto.setPodeTrocarTamanho(assinatura.podeTrocarTamanho());
        dto.setEnderecoEntrega(assinatura.getEnderecoEntrega());
        dto.setObservacoes(assinatura.getObservacoes());
        dto.setResumo(assinatura.getResumo());
        return dto;
    }

    /**
     * Atualiza membro existente com dados do DTO.
     */
    public static void updateMembro(MembroAssinatura membro, MembroAssinaturaRequestDTO dto) {
        membro.setNome(dto.getNome());
        membro.setTamanho(dto.getTamanho());
        membro.setTimeFavorito(dto.getTimeFavorito());
        membro.setJogadorFavorito(dto.getJogadorFavorito());
        membro.setNumeroFavorito(dto.getNumeroFavorito());
        if (dto.getEmail() != null) {
            membro.setEmail(dto.getEmail());
        }
    }
}
