package projeto.backend.service;

import org.springframework.stereotype.Service;
import projeto.backend.dto.LivroRequestDto;
import projeto.backend.entity.Livro;
import projeto.backend.mapper.LivroMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    private final List<Livro> livros = new ArrayList<>();
    private final LivroMapper mapper;

    public LivroService(LivroMapper mapper) {
        this.mapper = mapper;
    }

    public Livro criarLivro(LivroRequestDto dto) {
        Livro livro = mapper.toEntity(dto);
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

    public Livro atualizarLivro(Integer id, LivroRequestDto dto) {
        Livro livro = buscarPorId(id);

        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setAno(dto.getAno());
        livro.setDisponivel(dto.getDisponivel());

        return livro;
    }

    public void deletarLivro(Integer id) {
        Livro livro = buscarPorId(id);
        livros.remove(livro);
    }

    public List<Livro> listarLivrosDisponiveis() {
        return livros.stream()
                .filter(Livro::getDisponivel)
                .toList();
    }

    public void atualizarDisponibilidade(Integer livroId, Boolean disponivel) {
        Livro livro = buscarPorId(livroId);
        livro.setDisponivel(disponivel);
    }
}