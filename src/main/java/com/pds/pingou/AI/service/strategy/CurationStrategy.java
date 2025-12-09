package com.pds.pingou.AI.service.strategy;

import com.pds.pingou.pacote.ItemPacoteRequestDTO;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.security.user.User;

import java.util.List;

/**
 * Interface Strategy para algoritmos de curadoria de pacotes.
 * 
 * Permite variar a lógica de escolha de produtos baseada no tipo de plano.
 */
public interface CurationStrategy {

    /**
     * Sugere itens para um pacote baseado no perfil do usuário e regras do plano.
     * 
     * @param user   Usuário que receberá o pacote
     * @param plano  Plano de assinatura
     * @param limite Quantidade de itens
     * @return Lista de itens sugeridos
     */
    List<ItemPacoteRequestDTO> suggestPackageItems(User user, Plano plano, int limite);
}
