package com.pds.pingou.produto.hq;

import com.pds.pingou.produto.hq.exception.HqNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciamento de HQs.
 *
 * Fornece operações CRUD e buscas específicas para histórias em quadrinhos.
 *
 * @author Pingou Team
 * @version 1.0
 * @since 2.0
 */
@Service
public class HqService {

    @Autowired
    private HqRepository hqRepository;

    public List<HqResponseDTO> listarTodas() {
        return hqRepository.findAll().stream()
                .map(HqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HqResponseDTO> listarAtivas() {
        return hqRepository.findByAtivoTrue().stream()
                .map(HqMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca uma HQ por ID.
     *
     * @param id ID da HQ
     * @return DTO da HQ encontrada
     * @throws HqNotFoundException se a HQ não for encontrada
     */
    public HqResponseDTO buscarPorId(Long id) {
        Hq hq = hqRepository.findById(id)
                .orElseThrow(() -> new HqNotFoundException(id));
        return HqMapper.toDTO(hq);
    }

    /**
     * Busca a entidade HQ por ID (para uso interno).
     */
    public Hq buscarEntidadePorId(Long id) {
        return hqRepository.findById(id)
                .orElseThrow(() -> new HqNotFoundException(id));
    }

    /**
     * Cria uma nova HQ.
     *
     * @param dto Dados da HQ a ser criada
     * @return DTO da HQ criada
     */
    @Transactional
    public HqResponseDTO criar(HqRequestDTO dto) {
        Hq hq = HqMapper.toEntity(dto);
        hq.setAtivo(true); // Nova HQ sempre começa ativa
        hq = hqRepository.save(hq);
        return HqMapper.toDTO(hq);
    }

    /**
     * Atualiza uma HQ existente.
     *
     * @param id ID da HQ a ser atualizada
     * @param dto Novos dados da HQ
     * @return DTO da HQ atualizada
     * @throws HqNotFoundException se a HQ não for encontrada
     */
    @Transactional
    public HqResponseDTO atualizar(Long id, HqRequestDTO dto) {
        Hq hq = hqRepository.findById(id)
                .orElseThrow(() -> new HqNotFoundException(id));

        HqMapper.updateEntity(hq, dto);
        hq = hqRepository.save(hq);
        return HqMapper.toDTO(hq);
    }

    /**
     * Deleta uma HQ (soft delete - marca como inativa).
     *
     * @param id ID da HQ a ser deletada
     * @throws HqNotFoundException se a HQ não for encontrada
     */
    @Transactional
    public void deletar(Long id) {
        Hq hq = hqRepository.findById(id)
                .orElseThrow(() -> new HqNotFoundException(id));

        hq.setAtivo(false);
        hqRepository.save(hq);
    }

    @Transactional
    public HqResponseDTO ativar(Long id) {
        Hq hq = hqRepository.findById(id)
                .orElseThrow(() -> new HqNotFoundException(id));

        hq.setAtivo(true);
        hq = hqRepository.save(hq);
        return HqMapper.toDTO(hq);
    }

    public List<HqResponseDTO> buscarPorTipo(TipoHq tipo) {
        return hqRepository.findByTipoHqAndAtivoTrue(tipo).stream()
                .map(HqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HqResponseDTO> buscarPorCategoria(CategoriaHq categoria) {
        return hqRepository.findByCategoriaAndAtivoTrue(categoria).stream()
                .map(HqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HqResponseDTO> buscarPorEditora(EditoraHq editora) {
        return hqRepository.findByEditoraAndAtivoTrue(editora).stream()
                .map(HqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HqResponseDTO> buscarPorAno(Integer ano) {
        return hqRepository.findByAnoPublicacaoAndAtivoTrue(ano).stream()
                .map(HqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HqResponseDTO> buscarEdicoesColecionador() {
        return hqRepository.findEdicoesColecionadorAtivas().stream()
                .map(HqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HqResponseDTO> buscarPorNome(String nome) {
        return hqRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome).stream()
                .map(HqMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean existeEstaAtiva(Long id) {
        return hqRepository.findById(id)
                .map(Hq::getAtivo)
                .orElse(false);
    }

    public long contarAtivas() {
        return hqRepository.findByAtivoTrue().size();
    }
}