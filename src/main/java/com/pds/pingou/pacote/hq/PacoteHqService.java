package com.pds.pingou.pacote.hq;

import com.pds.pingou.pacote.exception.PacoteNotFoundException;
import com.pds.pingou.planos.hq.PlanoHq;
import com.pds.pingou.planos.hq.PlanoHqService;
import com.pds.pingou.produto.hq.Hq;
import com.pds.pingou.produto.hq.HqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacoteHqService {

    @Autowired
    private PacoteHqRepository pacoteHqRepository;

    @Autowired
    private PlanoHqService planoHqService;

    @Autowired
    private HqService hqService;

    @Autowired
    private ItemPacoteHqRepository itemPacoteHqRepository;

    public List<PacoteHqResponseDTO> listarTodos() {
        return pacoteHqRepository.findAll().stream()
                .map(PacoteHqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PacoteHqResponseDTO buscarPorId(Long id) {
        PacoteHq pacote = pacoteHqRepository.findById(id)
                .orElseThrow(() -> new PacoteNotFoundException(id));
        return PacoteHqMapper.toDTO(pacote);
    }

    public PacoteHq buscarEntidadePorId(Long id) {
        return pacoteHqRepository.findById(id)
                .orElseThrow(() -> new PacoteNotFoundException(id));
    }

    public List<PacoteHqResponseDTO> buscarPorPlano(Long planoId) {
        PlanoHq plano = planoHqService.buscarEntidadePorId(planoId);
        return pacoteHqRepository.findByPlano(plano).stream()
                .map(PacoteHqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PacoteHqResponseDTO> buscarPorMesEAno(Integer mes, Integer ano) {
        return pacoteHqRepository.findByMesAndAno(mes, ano).stream()
                .map(PacoteHqMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PacoteHqResponseDTO criar(PacoteHqRequestDTO dto) {
        PlanoHq plano = planoHqService.buscarEntidadePorId(dto.getPlanoId());

        PacoteHq pacote = PacoteHqMapper.toEntity(dto, plano);
        pacote.setAtivo(true);
        pacote = pacoteHqRepository.save(pacote);

        // Adicionar itens se fornecidos
        if (dto.getItens() != null && !dto.getItens().isEmpty()) {
            adicionarItensToPacote(pacote, dto.getItens());
            pacote = pacoteHqRepository.save(pacote);
        }

        return PacoteHqMapper.toDTO(pacote);
    }

    @Transactional
    public PacoteHqResponseDTO atualizar(Long id, PacoteHqRequestDTO dto) {
        PacoteHq pacote = pacoteHqRepository.findById(id)
                .orElseThrow(() -> new PacoteNotFoundException(id));

        PacoteHqMapper.updateEntity(pacote, dto);
        pacote = pacoteHqRepository.save(pacote);
        return PacoteHqMapper.toDTO(pacote);
    }

    @Transactional
    public void deletar(Long id) {
        PacoteHq pacote = pacoteHqRepository.findById(id)
                .orElseThrow(() -> new PacoteNotFoundException(id));
        pacoteHqRepository.delete(pacote);
    }

    @Transactional
    public PacoteHqResponseDTO adicionarItem(Long pacoteId, ItemPacoteHqRequestDTO itemDto) {
        PacoteHq pacote = pacoteHqRepository.findById(pacoteId)
                .orElseThrow(() -> new PacoteNotFoundException(pacoteId));

        Hq hq = hqService.buscarEntidadePorId(itemDto.getHqId());

        int totalItensAtual = pacote.getItens().stream()
                .mapToInt(ItemPacoteHq::getQuantidade)
                .sum();

        if (totalItensAtual + itemDto.getQuantidade() > pacote.getPlano().getMaxHqsPorMes()) {
            throw new RuntimeException("Quantidade de HQs excede o limite do plano: " +
                    pacote.getPlano().getMaxHqsPorMes());
        }

        ItemPacoteHq item = ItemPacoteHqMapper.toEntity(itemDto, pacote, hq);
        pacote.adicionarItem(item);

        pacote = pacoteHqRepository.save(pacote);
        return PacoteHqMapper.toDTO(pacote);
    }

    @Transactional
    public PacoteHqResponseDTO removerItem(Long pacoteId, Long itemId) {
        PacoteHq pacote = pacoteHqRepository.findById(pacoteId)
                .orElseThrow(() -> new PacoteNotFoundException(pacoteId));

        ItemPacoteHq item = itemPacoteHqRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado: " + itemId));

        pacote.removerItem(item);
        itemPacoteHqRepository.delete(item);

        pacote = pacoteHqRepository.save(pacote);
        return PacoteHqMapper.toDTO(pacote);
    }

    public List<ItemPacoteHqResponseDTO> listarItensDoPacote(Long pacoteId) {
        PacoteHq pacote = pacoteHqRepository.findById(pacoteId)
                .orElseThrow(() -> new PacoteNotFoundException(pacoteId));

        return pacote.getItens().stream()
                .map(ItemPacoteHqMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void adicionarItensToPacote(PacoteHq pacote, List<ItemPacoteHqRequestDTO> itensDto) {
        for (ItemPacoteHqRequestDTO itemDto : itensDto) {
            adicionarItemToPacote(pacote, itemDto);
        }
    }

    private void adicionarItemToPacote(PacoteHq pacote, ItemPacoteHqRequestDTO itemDto) {
        Hq hq = hqService.buscarEntidadePorId(itemDto.getHqId());

        if (!hq.getAtivo()) {
            throw new RuntimeException("HQ inativa não pode ser adicionada ao pacote: " + itemDto.getHqId());
        }

        if (itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }

        int totalItensAtual = pacote.getItens().stream()
                .mapToInt(ItemPacoteHq::getQuantidade)
                .sum();

        if (totalItensAtual + itemDto.getQuantidade() > pacote.getPlano().getMaxHqsPorMes()) {
            throw new RuntimeException("Quantidade de HQs excede o limite do plano: " +
                    pacote.getPlano().getMaxHqsPorMes());
        }

        ItemPacoteHq item = ItemPacoteHqMapper.toEntity(itemDto, pacote, hq);
        pacote.adicionarItem(item);
    }
}