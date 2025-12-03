package projeto.backend.dto;

import lombok.Getter;
import projeto.backend.entity.Usuario;
import java.time.LocalDate;

@Getter
public class UsuarioResponseDto {

    private final Integer id;
    private final String nome;
    private final String email;
    private final String telefone;
    private final LocalDate dataCadastro;

    public UsuarioResponseDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.telefone = usuario.getTelefone();
        this.dataCadastro = usuario.getDataCadastro();
    }
}