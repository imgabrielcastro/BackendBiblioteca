package projeto.backend.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
public class LivroRequestDto {

    @NotBlank(message = "Informe o título do livro.")
    private final String titulo;

    @NotBlank(message = "Informe o autor do livro.")
    private final String autor;

    @NotNull(message = "Informe o ano de publicação.")
    @Min(value = 1000, message = "Ano de publicação inválido.")
    private final Integer ano;

    @NotNull(message = "Informe se o livro está disponível.")
    private final Boolean disponivel;

    public LivroRequestDto(String titulo, String autor, Integer ano, Boolean disponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.disponivel = disponivel;
    }
}