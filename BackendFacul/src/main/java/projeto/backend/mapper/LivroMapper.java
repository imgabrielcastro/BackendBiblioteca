package projeto.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projeto.backend.dto.LivroRequestDto;
import projeto.backend.dto.LivroResponseDto;
import projeto.backend.entity.Livro;
import projeto.backend.entity.Categoria;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "disponivel", source = "disponivel", defaultValue = "true")
    Livro toEntity(LivroRequestDto dto);

    LivroResponseDto toResponseDto(Livro livro);

    List<LivroResponseDto> toResponseDtoList(List<Livro> livros);

    default Categoria mapCategoriaId(Integer categoriaId) {
        if (categoriaId == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        return categoria;
    }
}