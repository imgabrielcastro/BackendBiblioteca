package projeto.backend.dto;

import lombok.Getter;
import projeto.backend.entity.Livro;

@Getter
public class LivroResponseDto {

    private final Integer id;
    private final String titulo;
    private final String autor;
    private final Integer ano;
    private final Boolean disponivel;
    private final Integer categoriaId;
    private final String categoriaNome;

    public LivroResponseDto(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.autor = livro.getAutor();
        this.ano = livro.getAno();
        this.disponivel = livro.getDisponivel();
        this.categoriaId = livro.getCategoria() != null ? livro.getCategoria().getId() : null;
        this.categoriaNome = livro.getCategoria() != null ? livro.getCategoria().getNome() : null;
    }
}