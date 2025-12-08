package com.pds.pingou.produto;

import com.pds.pingou.produto.dto.ProdutoRequestDTO;
import com.pds.pingou.produto.dto.ProdutoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller REST para gerenciamento de produtos.
 * 
 * @author Pingou Framework Team
 * @version 2.0
 */
@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {
    
    private final ProdutoService produtoService;
    
    @GetMapping
    @Operation(summary = "Listar produtos ativos", description = "Retorna todos os produtos ativos do sistema")
    public ResponseEntity<List<ProdutoResponseDTO>> listarAtivos() {
        return ResponseEntity.ok(produtoService.listarAtivos());
    }
    
    @GetMapping("/todos")
    @Operation(summary = "Listar todos os produtos", description = "Retorna todos os produtos (ativos e inativos)")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo ID")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar produtos por nome", description = "Busca produtos pelo nome (busca parcial)")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }
    
    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Buscar produtos por categoria", description = "Retorna produtos de uma categoria específica")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(produtoService.buscarPorCategoria(categoria));
    }
    
    @GetMapping("/marca/{marca}")
    @Operation(summary = "Buscar produtos por marca", description = "Retorna produtos de uma marca específica")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorMarca(@PathVariable String marca) {
        return ResponseEntity.ok(produtoService.buscarPorMarca(marca));
    }
    
    @GetMapping("/preco")
    @Operation(summary = "Buscar produtos por faixa de preço", description = "Retorna produtos dentro de uma faixa de preço")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorFaixaDePreco(
            @RequestParam BigDecimal min,
            @RequestParam BigDecimal max) {
        return ResponseEntity.ok(produtoService.buscarPorFaixaDePreco(min, max));
    }
    
    @GetMapping("/categorias")
    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias de produtos disponíveis")
    public ResponseEntity<List<String>> listarCategorias() {
        return ResponseEntity.ok(produtoService.listarCategorias());
    }
    
    @GetMapping("/marcas")
    @Operation(summary = "Listar marcas", description = "Retorna todas as marcas de produtos disponíveis")
    public ResponseEntity<List<String>> listarMarcas() {
        return ResponseEntity.ok(produtoService.listarMarcas());
    }
    
    @PostMapping
    @Operation(summary = "Criar produto", description = "Cria um novo produto no sistema")
    public ResponseEntity<ProdutoResponseDTO> criar(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO response = produtoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza um produto existente")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.ok(produtoService.atualizar(id, dto));
    }
    
    @PatchMapping("/{id}/desativar")
    @Operation(summary = "Desativar produto", description = "Desativa um produto (soft delete)")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        produtoService.desativar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar produto", description = "Reativa um produto desativado")
    public ResponseEntity<Void> ativar(@PathVariable Long id) {
        produtoService.ativar(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir produto", description = "Exclui permanentemente um produto (use desativar quando possível)")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
