package projeto.backend.resource;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.backend.dto.LivroRequestDto;
import projeto.backend.dto.LivroResponseDto;
import projeto.backend.entity.Livro;
import projeto.backend.mapper.LivroMapper;
import projeto.backend.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/livros")
@Tag(name = "Livros", description = "Operações relacionadas aos livros")
public class LivroResource {

    private final LivroService service;
    private final LivroMapper mapper;

    public LivroResource(LivroService service, LivroMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cria um novo livro")
    public ResponseEntity<LivroResponseDto> criar(@Valid @RequestBody LivroRequestDto dto) {
        Livro livro = service.criarLivro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(livro));
    }

    @GetMapping
    @Operation(summary = "Lista todos os livros")
    public ResponseEntity<List<LivroResponseDto>> listar() {
        List<LivroResponseDto> dtos = mapper.toResponseDtoList(service.listarLivros());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Lista todos os livros disponíveis")
    public ResponseEntity<List<LivroResponseDto>> listarDisponiveis() {
        List<LivroResponseDto> dtos = mapper.toResponseDtoList(service.listarLivrosDisponiveis());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um livro por ID")
    public ResponseEntity<LivroResponseDto> buscarPorId(@PathVariable Integer id) {
        Livro livro = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponseDto(livro));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um livro")
    public ResponseEntity<LivroResponseDto> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody LivroRequestDto dto) {
        Livro atualizado = service.atualizarLivro(id, dto);
        return ResponseEntity.ok(mapper.toResponseDto(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um livro")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}