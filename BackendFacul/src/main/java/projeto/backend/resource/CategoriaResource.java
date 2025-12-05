package projeto.backend.resource;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.backend.dto.CategoriaRequestDto;
import projeto.backend.dto.CategoriaResponseDto;
import projeto.backend.entity.Categoria;
import projeto.backend.mapper.CategoriaMapper;
import projeto.backend.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Operações relacionadas às categorias de livros")
public class CategoriaResource {

    private final CategoriaService service;
    private final CategoriaMapper mapper;

    public CategoriaResource(CategoriaService service, CategoriaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova categoria")
    public ResponseEntity<CategoriaResponseDto> criar(@Valid @RequestBody CategoriaRequestDto dto) {
        Categoria categoria = service.criarCategoria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(categoria));
    }

    @GetMapping
    @Operation(summary = "Lista todas as categorias")
    public ResponseEntity<List<CategoriaResponseDto>> listar() {
        List<CategoriaResponseDto> dtos = mapper.toResponseDtoList(service.listarCategorias());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca categoria por ID")
    public ResponseEntity<CategoriaResponseDto> buscarPorId(@PathVariable Integer id) {
        Categoria categoria = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponseDto(categoria));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma categoria")
    public ResponseEntity<CategoriaResponseDto> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaRequestDto dto) {
        Categoria atualizada = service.atualizarCategoria(id, dto);
        return ResponseEntity.ok(mapper.toResponseDto(atualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma categoria")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}