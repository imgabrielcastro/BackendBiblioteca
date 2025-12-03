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
@Tag(name = "Empréstimos", description = "Operações relacionadas aos empréstimos")
public class EmprestimoResource {

    private final EmprestimoService service;
    private final EmprestimoMapper mapper;

    public EmprestimoResource(EmprestimoService service, EmprestimoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cria um novo empréstimo")
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
    @Operation(summary = "Lista todos os empréstimos em aberto")
    public ResponseEntity<List<EmprestimoResponseDto>> listarAbertos() {
        List<EmprestimoResponseDto> dtos = mapper.toResponseDtoList(service.listarEmprestimosAbertos());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista empréstimos por usuário")
    public ResponseEntity<List<EmprestimoResponseDto>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<EmprestimoResponseDto> dtos = mapper.toResponseDtoList(service.listarEmprestimosPorUsuario(usuarioId));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/atrasados")
    @Operation(summary = "Lista empréstimos atrasados")
    public ResponseEntity<List<EmprestimoResponseDto>> listarAtrasados() {
        List<EmprestimoResponseDto> dtos = mapper.toResponseDtoList(service.listarAtrasados());
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/{id}/devolver")
    @Operation(summary = "Registra a devolução de um empréstimo")
    public ResponseEntity<EmprestimoResponseDto> devolver(@PathVariable Integer id) {
        Emprestimo emprestimo = service.registrarDevolucao(id);
        return ResponseEntity.ok(mapper.toResponseDto(emprestimo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um empréstimo")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletarEmprestimo(id);
        return ResponseEntity.noContent().build();
    }
}