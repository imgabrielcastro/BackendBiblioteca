package projeto.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projeto.backend.dto.MultaRequestDto;
import projeto.backend.dto.MultaResponseDto;
import projeto.backend.entity.Multa;
import projeto.backend.entity.Emprestimo;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MultaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "emprestimo", ignore = true)
    @Mapping(target = "dataMulta", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "dataPagamento", ignore = true)
    @Mapping(target = "paga", constant = "false")
    Multa toEntity(MultaRequestDto dto);

    MultaResponseDto toResponseDto(Multa multa);

    List<MultaResponseDto> toResponseDtoList(List<Multa> multas);

    default Emprestimo mapEmprestimoId(Integer emprestimoId) {
        if (emprestimoId == null) {
            return null;
        }
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setId(emprestimoId);
        return emprestimo;
    }
}