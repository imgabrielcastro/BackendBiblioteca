package projeto.backend.dto;

import lombok.Getter;
import projeto.backend.entity.Multa;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class MultaResponseDto {

    private final Integer id;
    private final Integer emprestimoId;
    private final BigDecimal valor;
    private final LocalDate dataMulta;
    private final LocalDate dataPagamento;
    private final Boolean paga;

    public MultaResponseDto(Multa multa) {
        this.id = multa.getId();
        this.emprestimoId = multa.getEmprestimo().getId();
        this.valor = multa.getValor();
        this.dataMulta = multa.getDataMulta();
        this.dataPagamento = multa.getDataPagamento();
        this.paga = multa.getPaga();
    }
}