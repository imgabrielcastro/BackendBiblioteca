package projeto.backend.service;

import org.springframework.stereotype.Service;
import projeto.backend.entity.Multa;
import projeto.backend.dto.MultaRequestDto;
import projeto.backend.entity.Emprestimo;
import projeto.backend.enums.StatusEmprestimoEnum;
import projeto.backend.mapper.MultaMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MultaService {

    private final List<Multa> multas = new ArrayList<>();
    private final MultaMapper mapper;
    private final EmprestimoService emprestimoService;

    public MultaService(MultaMapper mapper, EmprestimoService emprestimoService) {
        this.mapper = mapper;
        this.emprestimoService = emprestimoService;
    }

    public Multa criarMulta(MultaRequestDto dto) {
        Emprestimo emprestimo = emprestimoService.buscarPorId(dto.getEmprestimoId());

        if (emprestimo.getStatus() == StatusEmprestimoEnum.DEVOLVIDO) {
            throw new RuntimeException("Não é possível criar multa para empréstimo já devolvido");
        }

        for (Multa m : multas) {
            if (m.getEmprestimo().getId().equals(dto.getEmprestimoId()) && !m.getPaga()) {
                throw new RuntimeException("Já existe multa em aberto para este empréstimo");
            }
        }

        Multa multa = mapper.toEntity(dto);
        multa.setEmprestimo(emprestimo);
        multa.setValor(dto.getValor());

        multa.setId(multas.size() + 1);
        multas.add(multa);

        return multa;
    }

    public List<Multa> listarMultas() {
        return multas;
    }

    public Multa buscarPorId(Integer id) {
        return multas.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Multa não encontrada"));
    }

    public List<Multa> listarMultasNaoPagas() {
        return multas.stream()
                .filter(m -> !m.getPaga())
                .toList();
    }

    public List<Multa> listarMultasPorUsuario(Integer usuarioId) {
        return multas.stream()
                .filter(m -> m.getEmprestimo().getUsuario().getId().equals(usuarioId))
                .toList();
    }

    public Multa pagarMulta(Integer id) {
        Multa multa = buscarPorId(id);

        if (multa.getPaga()) {
            throw new RuntimeException("Esta multa já foi paga");
        }

        multa.setPaga(true);
        multa.setDataPagamento(LocalDate.now());

        return multa;
    }

    public void deletarMulta(Integer id) {
        Multa multa = buscarPorId(id);

        if (multa.getPaga()) {
            throw new RuntimeException("Não é possível excluir multa já paga");
        }

        multas.remove(multa);
    }

    public BigDecimal calcularMultasAtrasadasAutomaticamente() {
        BigDecimal total = BigDecimal.ZERO;
        LocalDate hoje = LocalDate.now();
        BigDecimal valorMultaDiaria = new BigDecimal("2.00");

        List<Emprestimo> emprestimosAtrasados = emprestimoService.listarEmprestimosAtrasados();

        for (Emprestimo emprestimo : emprestimosAtrasados) {
            boolean multaExistente = multas.stream()
                    .anyMatch(m -> m.getEmprestimo().getId().equals(emprestimo.getId()) && !m.getPaga());

            if (!multaExistente) {
                long diasAtraso = hoje.toEpochDay() - emprestimo.getDataPrevista().toEpochDay();
                BigDecimal valorMulta = valorMultaDiaria.multiply(BigDecimal.valueOf(diasAtraso));

                MultaRequestDto dto = new MultaRequestDto(emprestimo.getId(), valorMulta);
                criarMulta(dto);

                total = total.add(valorMulta);
            }
        }

        return total;
    }
}