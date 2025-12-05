package projeto.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.Year;

@Entity
@Table(name = "livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotBlank
    @Column(nullable = false)
    private String autor;

    @NotNull
    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Boolean disponivel = true;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}