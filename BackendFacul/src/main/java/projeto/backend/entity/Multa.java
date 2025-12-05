package projeto.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "multa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "emprestimo_id", nullable = false)
    private Emprestimo emprestimo;

    @NotNull
    @Column(nullable = false)
    private BigDecimal valor;

    @Column(name = "data_multa", nullable = false)
    private LocalDate dataMulta = LocalDate.now();

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @Column(nullable = false)
    private Boolean paga = false;
}