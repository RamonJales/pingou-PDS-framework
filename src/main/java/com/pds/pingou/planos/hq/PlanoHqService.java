package com.pds.pingou.planos.hq;

import com.pds.pingou.planos.exception.PlanoNomeDuplicadoException;
import com.pds.pingou.planos.exception.PlanoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanoHqService {

    @Autowired
    private PlanoHqRepository planoHqRepository;

    public List<PlanoHqResponseDTO> listarTodos() {
        return planoHqRepository.findAll().stream()
                .map(PlanoHqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PlanoHqResponseDTO> listarAtivos() {
        return planoHqRepository.findByAtivoTrue().stream()
                .map(PlanoHqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PlanoHqResponseDTO buscarPorId(Long id) {
        PlanoHq plano = planoHqRepository.findById(id)
                .orElseThrow(() -> new PlanoNotFoundException(id));
        return PlanoHqMapper.toDTO(plano);
    }

    public PlanoHq buscarEntidadePorId(Long id) {
        return planoHqRepository.findById(id)
                .orElseThrow(() -> new PlanoNotFoundException(id));
    }

    @Transactional
    public PlanoHqResponseDTO criar(PlanoHqRequestDTO dto) {
        if (planoHqRepository.existsByNome(dto.getNome())) {
            throw new PlanoNomeDuplicadoException(dto.getNome());
        }

        PlanoHq plano = PlanoHqMapper.toEntity(dto);
        plano.setAtivo(true);
        plano = planoHqRepository.save(plano);
        return PlanoHqMapper.toDTO(plano);
    }

    @Transactional
    public PlanoHqResponseDTO atualizar(Long id, PlanoHqRequestDTO dto) {
        PlanoHq plano = planoHqRepository.findById(id)
                .orElseThrow(() -> new PlanoNotFoundException(id));

        PlanoHqMapper.updateEntity(plano, dto);
        plano = planoHqRepository.save(plano);
        return PlanoHqMapper.toDTO(plano);
    }

    @Transactional
    public void deletar(Long id) {
        if (!planoHqRepository.existsById(id)) {
            throw new PlanoNotFoundException(id);
        }
        planoHqRepository.deleteById(id);
    }

    public List<PlanoHqResponseDTO> buscarPorTipo(TipoPlanoHq tipo) {
        return planoHqRepository.findByTipoPlanoAndAtivoTrue(tipo).stream()
                .map(PlanoHqMapper::toDTO)
                .collect(Collectors.toList());
    }
}