package projeto.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projeto.backend.dto.EmprestimoRequestDto;
import projeto.backend.dto.EmprestimoResponseDto;
import projeto.backend.entity.Emprestimo;
import projeto.backend.entity.Usuario;
import projeto.backend.entity.Livro;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "livro", ignore = true)
    @Mapping(target = "dataEmprestimo", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "status", expression = "java(projeto.backend.enums.StatusEmprestimoEnum.ABERTO)")
    @Mapping(target = "dataDevolucao", ignore = true)
    Emprestimo toEntity(EmprestimoRequestDto dto);

    EmprestimoResponseDto toResponseDto(Emprestimo emprestimo);

    List<EmprestimoResponseDto> toResponseDtoList(List<Emprestimo> emprestimos);

    default Usuario mapUsuarioId(Integer usuarioId) {
        if (usuarioId == null) {
            return null;
        }
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        return usuario;
    }

    default Livro mapLivroId(Integer livroId) {
        if (livroId == null) {
            return null;
        }
        Livro livro = new Livro();
        livro.setId(livroId);
        return livro;
    }
}