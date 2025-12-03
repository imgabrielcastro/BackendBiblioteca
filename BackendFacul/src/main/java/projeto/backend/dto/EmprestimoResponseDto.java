package projeto.backend.dto;

import lombok.Getter;
import projeto.backend.entity.Emprestimo;
import java.time.LocalDate;

@Getter
public class EmprestimoResponseDto {

    private final Integer id;
    private final Integer usuarioId;
    private final String usuarioNome;
    private final Integer livroId;
    private final String livroTitulo;
    private final LocalDate dataEmprestimo;
    private final LocalDate dataPrevista;
    private final LocalDate dataDevolucao;
    private final String status;

    public EmprestimoResponseDto(Emprestimo emprestimo) {
        this.id = emprestimo.getId();
        this.usuarioId = emprestimo.getUsuario().getId();
        this.usuarioNome = emprestimo.getUsuario().getNome();
        this.livroId = emprestimo.getLivro().getId();
        this.livroTitulo = emprestimo.getLivro().getTitulo();
        this.dataEmprestimo = emprestimo.getDataEmprestimo();
        this.dataPrevista = emprestimo.getDataPrevista();
        this.dataDevolucao = emprestimo.getDataDevolucao();
        this.status = emprestimo.getStatus().getDescricao();
    }
}