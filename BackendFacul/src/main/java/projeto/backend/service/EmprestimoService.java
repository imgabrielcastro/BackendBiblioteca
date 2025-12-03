package projeto.backend.service;

import org.springframework.stereotype.Service;
import projeto.backend.dto.EmprestimoRequestDto;
import projeto.backend.entity.Emprestimo;
import projeto.backend.entity.Livro;
import projeto.backend.entity.Usuario;
import projeto.backend.enums.StatusEmprestimoEnum;
import projeto.backend.mapper.EmprestimoMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmprestimoService {

    private final List<Emprestimo> emprestimos = new ArrayList<>();
    private final EmprestimoMapper mapper;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public EmprestimoService(
            EmprestimoMapper mapper,
            UsuarioService usuarioService,
            LivroService livroService
    ) {
        this.mapper = mapper;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    public Emprestimo criarEmprestimo(EmprestimoRequestDto dto) {
        Usuario usuario = usuarioService.buscarPorId(dto.getUsuarioId());
        Livro livro = livroService.buscarPorId(dto.getLivroId());

        if (!livro.getDisponivel()) {
            throw new RuntimeException("Livro não está disponível para empréstimo");
        }

        Emprestimo emprestimo = mapper.toEntity(dto);
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setId(emprestimos.size() + 1);

        livroService.atualizarDisponibilidade(livro.getId(), false);
        emprestimos.add(emprestimo);

        return emprestimo;
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimos;
    }

    public Emprestimo buscarPorId(Integer id) {
        return emprestimos.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }

    public List<Emprestimo> listarEmprestimosPorUsuario(Integer usuarioId) {
        return emprestimos.stream()
                .filter(e -> e.getUsuario().getId().equals(usuarioId))
                .toList();
    }

    public List<Emprestimo> listarEmprestimosAbertos() {
        return emprestimos.stream()
                .filter(e -> e.getStatus() == StatusEmprestimoEnum.ABERTO)
                .toList();
    }

    public Emprestimo registrarDevolucao(Integer emprestimoId) {
        Emprestimo emprestimo = buscarPorId(emprestimoId);

        if (emprestimo.getStatus() == StatusEmprestimoEnum.DEVOLVIDO) {
            throw new RuntimeException("Empréstimo já foi devolvido");
        }

        emprestimo.setStatus(StatusEmprestimoEnum.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());

        livroService.atualizarDisponibilidade(emprestimo.getLivro().getId(), true);

        return emprestimo;
    }

    public List<Emprestimo> listarAtrasados() {
        LocalDate hoje = LocalDate.now();
        return emprestimos.stream()
                .filter(e -> e.getStatus() == StatusEmprestimoEnum.ABERTO)
                .filter(e -> e.getDataPrevista().isBefore(hoje))
                .toList();
    }

    public void deletarEmprestimo(Integer id) {
        Emprestimo emprestimo = buscarPorId(id);

        if (emprestimo.getStatus() == StatusEmprestimoEnum.ABERTO) {
            throw new RuntimeException("Não é possível deletar um empréstimo em aberto");
        }

        emprestimos.remove(emprestimo);
    }
}