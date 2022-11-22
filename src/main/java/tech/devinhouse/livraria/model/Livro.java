package tech.devinhouse.livraria.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LIVROS")
@Data
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LIVRO")
    private Integer id;

    private String isbn;

    private String titulo;

    private String idioma;

    private Integer nroDePaginas;

    private LocalDateTime dataInclusao;

}
