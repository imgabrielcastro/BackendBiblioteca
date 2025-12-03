package projeto.backend.dto;

import lombok.Getter;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Getter
public class EmprestimoRequestDto {

    @NotNull(message = "Informe o ID do usuário.")
    private final Integer usuarioId;

    @NotNull(message = "Informe o ID do livro.")
    private final Integer livroId;

    @NotNull(message = "Informe a data prevista para devolução.")
    private final LocalDate dataPrevista;

    public EmprestimoRequestDto(Integer usuarioId, Integer livroId, LocalDate dataPrevista) {
        this.usuarioId = usuarioId;
        this.livroId = livroId;
        this.dataPrevista = dataPrevista;
    }
}