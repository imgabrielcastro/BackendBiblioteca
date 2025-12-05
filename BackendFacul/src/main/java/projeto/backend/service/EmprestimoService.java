package projeto.backend.service;

import org.springframework.stereotype.Service;
import projeto.backend.entity.Emprestimo;
import projeto.backend.dto.EmprestimoRequestDto;
import projeto.backend.entity.Usuario;
import projeto.backend.entity.Livro;
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
        Livro livro = livroService.buscarPorIdDisponivel(dto.getLivroId());

        Emprestimo emprestimo = mapper.toEntity(dto);
        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataPrevista(dto.getDataPrevista());

        livro.setDisponivel(false);

        emprestimo.setId(emprestimos.size() + 1);
        emprestimos.add(emprestimo);

        return emprestimo;
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimos;
    }

    public List<Emprestimo> listarEmprestimosAbertos() {
        return emprestimos.stream()
                .filter(e -> e.getStatus() == StatusEmprestimoEnum.ABERTO)
                .toList();
    }

    public Emprestimo buscarPorId(Integer id) {
        return emprestimos.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }

    public Emprestimo devolverEmprestimo(Integer id) {
        Emprestimo emprestimo = buscarPorId(id);

        if (emprestimo.getStatus() == StatusEmprestimoEnum.DEVOLVIDO) {
            throw new RuntimeException("Este empréstimo já foi devolvido");
        }

        emprestimo.setStatus(StatusEmprestimoEnum.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now());

        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);

        return emprestimo;
    }

    public List<Emprestimo> listarEmprestimosPorUsuario(Integer usuarioId) {
        usuarioService.buscarPorId(usuarioId);

        return emprestimos.stream()
                .filter(e -> e.getUsuario().getId().equals(usuarioId))
                .toList();
    }

    public List<Emprestimo> listarEmprestimosPorLivro(Integer livroId) {
        livroService.buscarPorId(livroId);

        return emprestimos.stream()
                .filter(e -> e.getLivro().getId().equals(livroId))
                .toList();
    }

    public List<Emprestimo> listarEmprestimosAtrasados() {
        LocalDate hoje = LocalDate.now();

        return emprestimos.stream()
                .filter(e -> e.getStatus() == StatusEmprestimoEnum.ABERTO)
                .filter(e -> e.getDataPrevista().isBefore(hoje))
                .toList();
    }
}