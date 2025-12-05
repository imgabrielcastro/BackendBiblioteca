package projeto.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "Objeto para cadastro/atualização de categoria")
public class CategoriaRequestDto {

    @NotBlank(message = "Informe o nome da categoria.")
    private final String nome;

    private final String descricao;

    public CategoriaRequestDto(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }
}