package projeto.backend.dto;

import lombok.Getter;
import projeto.backend.entity.Categoria;

@Getter
public class CategoriaResponseDto {

    private final Integer id;
    private final String nome;
    private final String descricao;

    public CategoriaResponseDto(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
        this.descricao = categoria.getDescricao();
    }
}