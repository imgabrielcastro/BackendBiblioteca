package projeto.backend.service;

import org.springframework.stereotype.Service;
import projeto.backend.entity.Livro;
import projeto.backend.dto.LivroRequestDto;
import projeto.backend.mapper.LivroMapper;
import projeto.backend.entity.Categoria;
import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    private final List<Livro> livros = new ArrayList<>();
    private final LivroMapper mapper;
    private final CategoriaService categoriaService;

    public LivroService(LivroMapper mapper, CategoriaService categoriaService) {
        this.mapper = mapper;
        this.categoriaService = categoriaService;
    }

    public Livro criarLivro(LivroRequestDto dto) {
        Livro livro = mapper.toEntity(dto);

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaService.buscarPorId(dto.getCategoriaId());
            livro.setCategoria(categoria);
        }

        livro.setId(livros.size() + 1);
        livros.add(livro);
        return livro;
    }

    public List<Livro> listarLivros() {
        return livros;
    }

    public Livro buscarPorId(Integer id) {
        return livros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    public Livro buscarPorIdDisponivel(Integer id) {
        Livro livro = buscarPorId(id);
        if (!livro.getDisponivel()) {
            throw new RuntimeException("Livro não está disponível para empréstimo");
        }
        return livro;
    }

    public List<Livro> listarDisponiveis() {
        return livros.stream()
                .filter(Livro::getDisponivel)
                .toList();
    }

    public Livro atualizarLivro(Integer id, LivroRequestDto dto) {
        Livro livro = buscarPorId(id);

        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setAno(dto.getAno());
        livro.setDisponivel(dto.getDisponivel());

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaService.buscarPorId(dto.getCategoriaId());
            livro.setCategoria(categoria);
        } else {
            livro.setCategoria(null);
        }

        return livro;
    }

    public void deletarLivro(Integer id) {
        Livro livro = buscarPorId(id);

        if (!livro.getDisponivel()) {
            throw new RuntimeException("Não é possível excluir livro que está emprestado");
        }

        livros.remove(livro);
    }
}