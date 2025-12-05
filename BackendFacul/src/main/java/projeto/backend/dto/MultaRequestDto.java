package projeto.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@Schema(description = "Objeto para cadastro de multa")
public class MultaRequestDto {

    @NotNull(message = "Informe o ID do empréstimo.")
    private final Integer emprestimoId;

    @NotNull(message = "Informe o valor da multa.")
    @Positive(message = "O valor deve ser positivo.")
    private final BigDecimal valor;

    public MultaRequestDto(Integer emprestimoId, BigDecimal valor) {
        this.emprestimoId = emprestimoId;
        this.valor = valor;
    }
}