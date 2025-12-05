package projeto.backend.resource;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.backend.dto.MultaRequestDto;
import projeto.backend.dto.MultaResponseDto;
import projeto.backend.entity.Multa;
import projeto.backend.mapper.MultaMapper;
import projeto.backend.service.MultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/multas")
@Tag(name = "Multas", description = "Operações relacionadas às multas por atraso")
public class MultaResource {

    private final MultaService service;
    private final MultaMapper mapper;

    public MultaResource(MultaService service, MultaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cria uma nova multa")
    public ResponseEntity<MultaResponseDto> criar(@Valid @RequestBody MultaRequestDto dto) {
        Multa multa = service.criarMulta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(multa));
    }

    @GetMapping
    @Operation(summary = "Lista todas as multas")
    public ResponseEntity<List<MultaResponseDto>> listar() {
        List<MultaResponseDto> dtos = mapper.toResponseDtoList(service.listarMultas());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/nao-pagas")
    @Operation(summary = "Lista multas não pagas")
    public ResponseEntity<List<MultaResponseDto>> listarNaoPagas() {
        List<MultaResponseDto> dtos = mapper.toResponseDtoList(service.listarMultasNaoPagas());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista multas por usuário")
    public ResponseEntity<List<MultaResponseDto>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<MultaResponseDto> dtos = mapper.toResponseDtoList(service.listarMultasPorUsuario(usuarioId));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca multa por ID")
    public ResponseEntity<MultaResponseDto> buscarPorId(@PathVariable Integer id) {
        Multa multa = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponseDto(multa));
    }

    @PutMapping("/{id}/pagar")
    @Operation(summary = "Registra pagamento de multa")
    public ResponseEntity<MultaResponseDto> pagar(@PathVariable Integer id) {
        Multa paga = service.pagarMulta(id);
        return ResponseEntity.ok(mapper.toResponseDto(paga));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma multa")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletarMulta(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calcular-atrasos")
    @Operation(summary = "Calcula multas automaticamente para empréstimos atrasados")
    public ResponseEntity<BigDecimal> calcularMultasAtrasadas() {
        BigDecimal total = service.calcularMultasAtrasadasAutomaticamente();
        return ResponseEntity.ok(total);
    }
}