package projeto.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projeto.backend.dto.LivroRequestDto;
import projeto.backend.dto.LivroResponseDto;
import projeto.backend.entity.Livro;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {
    @Mapping(target = "id", ignore = true)
    Livro toEntity(LivroRequestDto dto);

    LivroResponseDto toResponseDto(Livro livro);

    List<LivroResponseDto> toResponseDtoList(List<Livro> livros);
}