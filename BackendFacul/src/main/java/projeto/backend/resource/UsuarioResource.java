package projeto.backend.resource;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.backend.dto.UsuarioRequestDto;
import projeto.backend.dto.UsuarioResponseDto;
import projeto.backend.entity.Usuario;
import projeto.backend.mapper.UsuarioMapper;
import projeto.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
public class UsuarioResource {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    public UsuarioResource(UsuarioService service, UsuarioMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário")
    public ResponseEntity<UsuarioResponseDto> criar(@Valid @RequestBody UsuarioRequestDto dto) {
        Usuario usuario = service.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(usuario));
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        List<UsuarioResponseDto> dtos = mapper.toResponseDtoList(service.listarUsuarios());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Integer id) {
        Usuario usuario = service.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponseDto(usuario));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário")
    public ResponseEntity<UsuarioResponseDto> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioRequestDto dto) {
        Usuario atualizado = service.atualizarUsuario(id, dto);
        return ResponseEntity.ok(mapper.toResponseDto(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um usuário")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}