package com.pds.pingou.produto.hq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HqRepository extends JpaRepository<Hq, Long> {

    // Buscar por status ativo
    List<Hq> findByAtivoTrue();

    // Buscar por tipo e ativo
    @Query("SELECT h FROM Hq h WHERE h.tipoHq = :tipo AND h.ativo = true")
    List<Hq> findByTipoHqAndAtivoTrue(@Param("tipo") TipoHq tipo);

    // Buscar por categoria e ativo
    @Query("SELECT h FROM Hq h WHERE h.categoria = :categoria AND h.ativo = true")
    List<Hq> findByCategoriaAndAtivoTrue(@Param("categoria") CategoriaHq categoria);

    // Buscar por editora e ativo
    @Query("SELECT h FROM Hq h WHERE h.editora = :editora AND h.ativo = true")
    List<Hq> findByEditoraAndAtivoTrue(@Param("editora") EditoraHq editora);

    // Buscar por ano de publicação e ativo
    @Query("SELECT h FROM Hq h WHERE h.anoPublicacao = :ano AND h.ativo = true")
    List<Hq> findByAnoPublicacaoAndAtivoTrue(@Param("ano") Integer ano);

    // Buscar edições de colecionador ativas
    @Query("SELECT h FROM Hq h WHERE h.edicaoColecionador = true AND h.ativo = true")
    List<Hq> findEdicoesColecionadorAtivas();

    // Buscar por nome contendo (case insensitive) e ativo
    @Query("SELECT h FROM Hq h WHERE LOWER(h.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND h.ativo = true")
    List<Hq> findByNomeContainingIgnoreCaseAndAtivoTrue(@Param("nome") String nome);
}