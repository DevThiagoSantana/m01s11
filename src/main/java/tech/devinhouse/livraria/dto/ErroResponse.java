package tech.devinhouse.livraria.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ErroResponse {

    private String mensagem;

    private Map<String, String> detalhes;

    public ErroResponse(String mensagem) {
        this.mensagem = mensagem;
    }

    public ErroResponse(String mensagem, Map<String, String> details) {
        this(mensagem);
        this.detalhes = details;
    }

}
