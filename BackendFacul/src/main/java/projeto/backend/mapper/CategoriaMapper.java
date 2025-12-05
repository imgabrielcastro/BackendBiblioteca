package projeto.backend.mapper;

import org.mapstruct.Mapper;
import projeto.backend.dto.CategoriaRequestDto;
import projeto.backend.dto.CategoriaResponseDto;
import projeto.backend.entity.Categoria;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    Categoria toEntity(CategoriaRequestDto dto);

    CategoriaResponseDto toResponseDto(Categoria categoria);

    List<CategoriaResponseDto> toResponseDtoList(List<Categoria> categorias);
}