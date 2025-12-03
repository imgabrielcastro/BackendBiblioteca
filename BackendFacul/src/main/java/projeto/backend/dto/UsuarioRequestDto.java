package projeto.backend.dto;

import lombok.Getter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
public class UsuarioRequestDto {

    @NotBlank(message = "Informe o nome completo do usuário.")
    private final String nome;

    @Email(message = "Informe um e-mail válido.")
    @NotBlank(message = "O e-mail não pode ficar em branco.")
    private final String email;

    @NotBlank(message = "O telefone deve ser informado.")
    @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$", message = "Telefone inválido.")
    private final String telefone;

    public UsuarioRequestDto(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }
}