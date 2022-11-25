package tech.devinhouse.livraria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(title = "LivroResposta", description = "Representa a resposta da API em relação a um livro cadastrado")
public class LivroResponse {

    private Integer id;

    private String isbn;

    @Schema(title = "Título do Livro", description = "Titulo do livro contendo inclusive o sub-titulo e houver")
    private String titulo;

    private String idioma;

    private Integer nroDePaginas;

    private LocalDateTime dataInclusao;

}
