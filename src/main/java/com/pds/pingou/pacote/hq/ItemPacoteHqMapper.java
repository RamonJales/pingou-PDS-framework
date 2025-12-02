// src/main/java/com/pds/pingou/pacote/hq/ItemPacoteHqMapper.java
package com.pds.pingou.pacote.hq;

import com.pds.pingou.produto.hq.Hq;
import org.springframework.stereotype.Component;

@Component
public class ItemPacoteHqMapper {

    public static ItemPacoteHq toEntity(ItemPacoteHqRequestDTO dto, PacoteHq pacote, Hq hq) {
        ItemPacoteHq item = new ItemPacoteHq();
        item.setPacote(pacote);
        item.setHq(hq);
        item.setQuantidade(dto.getQuantidade());
        item.setObservacoes(dto.getObservacoes());
        item.setEdicaoEspecial(dto.getEdicaoEspecial() != null ? dto.getEdicaoEspecial() : false);
        return item;
    }

    public static ItemPacoteHqResponseDTO toDTO(ItemPacoteHq item) {
        ItemPacoteHqResponseDTO dto = new ItemPacoteHqResponseDTO();
        dto.setId(item.getId());
        dto.setHqId(item.getHq().getId());
        dto.setHqNome(item.getHq().getNome());
        dto.setHqPreco(item.getHq().getPreco());
        dto.setHqImagem(item.getHq().getUrlImagem());
        dto.setQuantidade(item.getQuantidade());
        dto.setObservacoes(item.getObservacoes());
        dto.setEdicaoEspecial(item.getEdicaoEspecial());
        return dto;
    }
}