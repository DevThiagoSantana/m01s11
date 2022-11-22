package tech.devinhouse.livraria.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LivroResponse {

    private Integer id;

    private String isbn;

    private String titulo;

    private String idioma;

    private Integer nroDePaginas;

    private LocalDateTime dataInclusao;

}
