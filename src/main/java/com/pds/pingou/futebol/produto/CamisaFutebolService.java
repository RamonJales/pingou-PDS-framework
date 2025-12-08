package com.pds.pingou.futebol.produto;

import com.pds.pingou.futebol.enums.Competicao;
import com.pds.pingou.futebol.enums.TipoCamisa;
import com.pds.pingou.futebol.produto.dto.CamisaFutebolRequestDTO;
import com.pds.pingou.futebol.produto.dto.CamisaFutebolResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service para operações de negócio relacionadas a camisas de futebol.
 */
@Service
public class CamisaFutebolService {

    @Autowired
    private CamisaFutebolRepository camisaRepository;

    /**
     * Lista todas as camisas ativas.
     */
    public List<CamisaFutebolResponseDTO> listarTodas() {
        return camisaRepository.findByAtivoTrue().stream()
                .map(CamisaFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca camisa por ID.
     */
    public CamisaFutebolResponseDTO buscarPorId(Long id) {
        CamisaFutebol camisa = camisaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camisa não encontrada: " + id));
        return CamisaFutebolMapper.toDTO(camisa);
    }

    /**
     * Busca camisas por time.
     */
    public List<CamisaFutebolResponseDTO> buscarPorTime(String time) {
        return camisaRepository.findByTimeContainingIgnoreCase(time).stream()
                .map(CamisaFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca camisas por tipo.
     */
    public List<CamisaFutebolResponseDTO> buscarPorTipo(TipoCamisa tipo) {
        return camisaRepository.findByTipoCamisa(tipo).stream()
                .map(CamisaFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca camisas por competição.
     */
    public List<CamisaFutebolResponseDTO> buscarPorCompeticao(Competicao competicao) {
        return camisaRepository.findByCompeticao(competicao).stream()
                .map(CamisaFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca camisas por temporada.
     */
    public List<CamisaFutebolResponseDTO> buscarPorTemporada(String temporada) {
        return camisaRepository.findByTemporada(temporada).stream()
                .map(CamisaFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova camisa.
     */
    @Transactional
    public CamisaFutebolResponseDTO criar(CamisaFutebolRequestDTO dto) {
        // Validar se já existe camisa igual
        if (camisaRepository.existsByTimeAndTemporadaAndTipoCamisa(
                dto.getTime(), dto.getTemporada(), dto.getTipoCamisa())) {
            throw new RuntimeException("Já existe uma camisa " + dto.getTipoCamisa().getNome() + 
                    " do " + dto.getTime() + " para a temporada " + dto.getTemporada());
        }
        
        CamisaFutebol camisa = CamisaFutebolMapper.toEntity(dto);
        camisa = camisaRepository.save(camisa);
        return CamisaFutebolMapper.toDTO(camisa);
    }

    /**
     * Atualiza uma camisa existente.
     */
    @Transactional
    public CamisaFutebolResponseDTO atualizar(Long id, CamisaFutebolRequestDTO dto) {
        CamisaFutebol camisa = camisaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camisa não encontrada: " + id));
        
        CamisaFutebolMapper.updateEntity(camisa, dto);
        camisa = camisaRepository.save(camisa);
        return CamisaFutebolMapper.toDTO(camisa);
    }

    /**
     * Ativa uma camisa.
     */
    @Transactional
    public void ativar(Long id) {
        CamisaFutebol camisa = camisaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camisa não encontrada: " + id));
        camisa.activate();
        camisaRepository.save(camisa);
    }

    /**
     * Desativa uma camisa.
     */
    @Transactional
    public void desativar(Long id) {
        CamisaFutebol camisa = camisaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camisa não encontrada: " + id));
        camisa.deactivate();
        camisaRepository.save(camisa);
    }

    /**
     * Deleta uma camisa.
     */
    @Transactional
    public void deletar(Long id) {
        if (!camisaRepository.existsById(id)) {
            throw new RuntimeException("Camisa não encontrada: " + id);
        }
        camisaRepository.deleteById(id);
    }

    /**
     * Lista todos os times disponíveis.
     */
    public List<String> listarTimes() {
        return camisaRepository.findAllTimes();
    }

    /**
     * Lista todas as temporadas disponíveis.
     */
    public List<String> listarTemporadas() {
        return camisaRepository.findAllTemporadas();
    }

    /**
     * Busca camisas recomendadas para um assinante baseado em seus times favoritos.
     */
    public List<CamisaFutebolResponseDTO> buscarRecomendadas(List<String> timesFavoritos) {
        return camisaRepository.findByTimesPreferidos(timesFavoritos).stream()
                .map(CamisaFutebolMapper::toDTO)
                .collect(Collectors.toList());
    }
}
