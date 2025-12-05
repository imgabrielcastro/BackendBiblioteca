package projeto.backend.resource;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.backend.dto.EmprestimoRequestDto;
import projeto.backend.dto.EmprestimoResponseDto;
import projeto.backend.entity.Emprestimo;
import projeto.backend.mapper.EmprestimoMapper;
import projeto.backend.service.EmprestimoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
@Tag(name = "Empréstimos", description = "Operações relacionadas aos empréstimos de livros")
public class EmprestimoResource {

    private final EmprestimoService service;
    private final EmprestimoMapper mapper;

    public EmprestimoResource(EmprestimoService service, EmprestimoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Realiza um novo empréstimo")
    public ResponseEntity<EmprestimoResponseDto> criar(@Valid @RequestBody EmprestimoRequestDto dto) {
        Emprestimo emprestimo = service.criarEmprestimo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(emprestimo));
    }

    @GetMapping
    @Operation(summary = "Lista todos os empréstimos")
    public ResponseEntity<List<EmprestimoResponseDto>> listar() {
        List<EmprestimoResponseDto> dtos = mapper.toResponseDtoList(service.listarEmprestimos());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/abertos")
    @Operation(summary = "Lista empréstimos em aberto")
    public ResponseEntity<List<EmprestimoResponseDto>> listarAbertos() {
        List<EmprestimoResponseDto> dtos = mapper.toResponseDtoList(service.listarEmprestimosAbertos());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/atrasados")
    @Operation(summary = "Lista empréstimos atrasados")
    public ResponseEntity<List<EmprestimoResponseDto>> listarAtrasados() {
        List<EmprestimoResponseDto> dtos = mapper.toResponseDtoList(service.listarEmprestimosAtrasados());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista empréstimos por usuário")
    public ResponseEntity<List<EmprestimoResponseDto>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<EmprestimoResponseDto> dtos = mapper.toResponseDtoList(service.listarEmprestimosPorUsuario(usuarioId));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/livro/{livroId}")
    @Operation(summary = "Lista empréstimos por livro")
    public ResponseEntity<List<EmprestimoResponseDto>> listarPorLivro(@PathVariable Integer livroId) {
        List<EmprestimoResponseDto> dtos = mapper.toResponseDtoList(service.listarEmprestimosPorLivro(livroId));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca empréstimo por ID")
    public ResponseEntity<EmprestimoResponseDto> buscarPorId(@PathVariable Integer id) {
        Emprestimo emprestimo = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponseDto(emprestimo));
    }

    @PutMapping("/{id}/devolver")
    @Operation(summary = "Registra devolução de empréstimo")
    public ResponseEntity<EmprestimoResponseDto> devolver(@PathVariable Integer id) {
        Emprestimo devolvido = service.devolverEmprestimo(id);
        return ResponseEntity.ok(mapper.toResponseDto(devolvido));
    }
}