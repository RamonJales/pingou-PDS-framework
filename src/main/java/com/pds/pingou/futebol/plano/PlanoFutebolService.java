package com.pds.pingou.futebol.plano;

import com.pds.pingou.futebol.enums.TipoPlanoFutebol;
import com.pds.pingou.futebol.plano.dto.PlanoFutebolRequestDTO;
import com.pds.pingou.futebol.plano.dto.PlanoFutebolResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para operações de negócio relacionadas a planos de futebol.
 */
@Service
public class PlanoFutebolService {

    @Autowired
    private PlanoFutebolRepository planoRepository;

    /**
     * Lista todos os planos ativos.
     */
    public List<PlanoFutebolResponseDTO> listarTodos() {
        return planoRepository.findByAtivoTrue().stream()
                .map(PlanoFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca plano por ID.
     */
    public PlanoFutebolResponseDTO buscarPorId(Long id) {
        PlanoFutebol plano = planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + id));
        return PlanoFutebolMapper.toDTO(plano);
    }

    /**
     * Busca plano por nome.
     */
    public PlanoFutebolResponseDTO buscarPorNome(String nome) {
        PlanoFutebol plano = planoRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + nome));
        return PlanoFutebolMapper.toDTO(plano);
    }

    /**
     * Lista planos por tipo.
     */
    public List<PlanoFutebolResponseDTO> listarPorTipo(TipoPlanoFutebol tipo) {
        return planoRepository.findByTipoPlanoAndAtivoTrue(tipo).stream()
                .map(PlanoFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista planos familiares (mais de 1 membro).
     */
    public List<PlanoFutebolResponseDTO> listarPlanosFamiliares() {
        return planoRepository.findByAtivoTrue().stream()
                .filter(p -> p.isFamiliar())
                .map(PlanoFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista planos individuais.
     */
    public List<PlanoFutebolResponseDTO> listarPlanosIndividuais() {
        return planoRepository.findByTipoPlanoAndAtivoTrue(TipoPlanoFutebol.INDIVIDUAL).stream()
                .map(PlanoFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista planos com personalização inclusa.
     */
    public List<PlanoFutebolResponseDTO> listarPlanosComPersonalizacao() {
        return planoRepository.findByPersonalizacaoInclusaTrue().stream()
                .map(PlanoFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria um novo plano.
     */
    @Transactional
    public PlanoFutebolResponseDTO criar(PlanoFutebolRequestDTO dto) {
        if (planoRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Já existe um plano com o nome: " + dto.getNome());
        }
        
        PlanoFutebol plano = PlanoFutebolMapper.toEntity(dto);
        plano = planoRepository.save(plano);
        return PlanoFutebolMapper.toDTO(plano);
    }

    /**
     * Atualiza um plano existente.
     */
    @Transactional
    public PlanoFutebolResponseDTO atualizar(Long id, PlanoFutebolRequestDTO dto) {
        PlanoFutebol plano = planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + id));
        
        // Verificar se o novo nome já existe em outro plano
        if (!plano.getNome().equals(dto.getNome()) && planoRepository.existsByNome(dto.getNome())) {
            throw new RuntimeException("Já existe um plano com o nome: " + dto.getNome());
        }
        
        PlanoFutebolMapper.updateEntity(plano, dto);
        plano = planoRepository.save(plano);
        return PlanoFutebolMapper.toDTO(plano);
    }

    /**
     * Ativa um plano.
     */
    @Transactional
    public void ativar(Long id) {
        PlanoFutebol plano = planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + id));
        plano.activate();
        planoRepository.save(plano);
    }

    /**
     * Desativa um plano.
     */
    @Transactional
    public void desativar(Long id) {
        PlanoFutebol plano = planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + id));
        plano.deactivate();
        planoRepository.save(plano);
    }

    /**
     * Deleta um plano.
     */
    @Transactional
    public void deletar(Long id) {
        if (!planoRepository.existsById(id)) {
            throw new RuntimeException("Plano não encontrado: " + id);
        }
        planoRepository.deleteById(id);
    }

    /**
     * Sugere o melhor plano para uma quantidade de membros.
     */
    public PlanoFutebolResponseDTO sugerirPlanoParaMembros(int quantidadeMembros) {
        TipoPlanoFutebol tipoSugerido = TipoPlanoFutebol.getPlanoParaMembros(quantidadeMembros);
        
        return planoRepository.findByTipoPlanoAndAtivoTrue(tipoSugerido).stream()
                .findFirst()
                .map(PlanoFutebolMapper::toDTO)
                .orElseThrow(() -> new RuntimeException(
                    "Não há plano disponível para " + quantidadeMembros + " membros"));
    }
}
