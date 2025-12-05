package projeto.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "Objeto para cadastro/atualização de livro")
public class LivroRequestDto {

    @NotBlank(message = "Informe o título do livro.")
    private final String titulo;

    @NotBlank(message = "Informe o autor do livro.")
    private final String autor;

    @NotNull(message = "Informe o ano de publicação.")
    private final Integer ano;

    private final Boolean disponivel;

    private final Integer categoriaId;

    public LivroRequestDto(String titulo, String autor, Integer ano, Boolean disponivel, Integer categoriaId) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.disponivel = disponivel != null ? disponivel : true;
        this.categoriaId = categoriaId;
    }
}