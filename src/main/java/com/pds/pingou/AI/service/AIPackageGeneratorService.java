package com.pds.pingou.AI.service;

import com.pds.pingou.AI.dto.GeneratePackageRequestDTO;
import com.pds.pingou.AI.dto.PackageSuggestionResponseDTO;
import com.pds.pingou.camisa.Camisa;
import com.pds.pingou.camisa.CamisaRepository;
import com.pds.pingou.camisa.pacote.ItemPacoteCamisa;
import com.pds.pingou.camisa.pacote.dto.ItemPacoteCamisaRequestDTO;
import com.pds.pingou.camisa.pacote.PacoteCamisa;
import com.pds.pingou.camisa.pacote.PacoteCamisaRepository;
import com.pds.pingou.camisa.planos.PlanoCamisa;
import com.pds.pingou.camisa.planos.PlanoCamisaRepository;
import com.pds.pingou.camisa.planos.exception.PlanoCamisaNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AIPackageGeneratorService {

    private final PlanoCamisaRepository planoRepository;
    private final PacoteCamisaRepository pacoteRepository;
    private final CamisaRepository camisaRepository;

    public AIPackageGeneratorService(PlanoCamisaRepository planoRepository,
                                     PacoteCamisaRepository pacoteRepository,
                                     CamisaRepository camisaRepository) {
        this.planoRepository = planoRepository;
        this.pacoteRepository = pacoteRepository;
        this.camisaRepository = camisaRepository;
    }

    public PackageSuggestionResponseDTO suggest(GeneratePackageRequestDTO req) {
        Long planoId = req.getPlanoId();
        PlanoCamisa plano = planoRepository.findById(planoId)
                .orElseThrow(() -> new PlanoCamisaNotFoundException(planoId));

        int limite = Optional.ofNullable(req.getTamanho())
                .filter(v -> v > 0)
                .map(v -> Math.min(v, Optional.ofNullable(plano.getMaxProdutosPorPeriodo()).orElse(1)))
                .orElse(Optional.ofNullable(plano.getMaxProdutosPorPeriodo()).orElse(1));

        // Lista de camisas ativas ordenadas por id para estabilidade
        List<Long> camisasAtivas = camisaRepository.findByAtivoTrue().stream()
                .map(Camisa::getId)
                .sorted()
                .collect(Collectors.toList());
        if (camisasAtivas.isEmpty()) {
            return new PackageSuggestionResponseDTO(planoId, limite, List.of());
        }

        // Conjuntos de combinações já usadas neste plano (como string de ids ordenados)
        Set<String> combinacoesUsadas = pacoteRepository.findByPlano(plano).stream()
                .map(PacoteCamisa::getItens)
                .filter(Objects::nonNull)
                .map(itens -> itens.stream()
                        .map(ItemPacoteCamisa::getCamisa)
                        .filter(Objects::nonNull)
                        .map(c -> c.getId())
                        .sorted()
                        .map(String::valueOf)
                        .collect(Collectors.joining("-")))
                .collect(Collectors.toSet());

        // Estratégia simples: usar um deslocamento baseado na quantidade de pacotes existentes
        int n = camisasAtivas.size();
        int offsetBase = Math.floorMod(combinacoesUsadas.size(), Math.max(1, n));

        // Tentar até n deslocamentos diferentes para evitar repetir combinações
        for (int attempt = 0; attempt < n; attempt++) {
            int offset = (offsetBase + attempt) % n;
            List<Long> selecionados = new ArrayList<>(limite);
            for (int i = 0; i < n && selecionados.size() < limite; i++) {
                Long id = camisasAtivas.get((offset + i) % n);
                if (!selecionados.contains(id)) selecionados.add(id);
            }

            List<Long> ordenados = selecionados.stream().sorted().toList();
            String assinatura = ordenados.stream().map(String::valueOf).collect(Collectors.joining("-"));
            if (!combinacoesUsadas.contains(assinatura)) {
                List<ItemPacoteCamisaRequestDTO> itens = selecionados.stream()
                        .map(pid -> {
                            ItemPacoteCamisaRequestDTO dto = new ItemPacoteCamisaRequestDTO();
                            dto.setCamisaId(pid);
                            dto.setQuantidade(1);
                            dto.setObservacoes("");
                            return dto;
                        })
                        .collect(Collectors.toList());
                return new PackageSuggestionResponseDTO(planoId, limite, itens);
            }
        }

        // Fallback: retornar a primeira combinação possível (mesmo que já usada)
        List<ItemPacoteCamisaRequestDTO> itens = camisasAtivas.stream()
                .limit(limite)
                .map(id -> {
                    ItemPacoteCamisaRequestDTO dto = new ItemPacoteCamisaRequestDTO();
                    dto.setCamisaId(id);
                    dto.setQuantidade(1);
                    dto.setObservacoes("");
                    return dto;
                })
                .collect(Collectors.toList());

        return new PackageSuggestionResponseDTO(planoId, limite, itens);
    }
}


