package projeto.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projeto.backend.dto.UsuarioRequestDto;
import projeto.backend.dto.UsuarioResponseDto;
import projeto.backend.entity.Usuario;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", expression = "java(java.time.LocalDate.now())")
    Usuario toEntity(UsuarioRequestDto dto);

    UsuarioResponseDto toResponseDto(Usuario usuario);

    List<UsuarioResponseDto> toResponseDtoList(List<Usuario> usuarios);
}