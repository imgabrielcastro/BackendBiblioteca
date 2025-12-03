package projeto.backend.enums;

import lombok.Getter;

@Getter
public enum StatusEmprestimoEnum {
    ABERTO("ABERTO"),
    DEVOLVIDO("DEVOLVIDO");

    private final String descricao;

    StatusEmprestimoEnum(String descricao) {
        this.descricao = descricao;
    }
}