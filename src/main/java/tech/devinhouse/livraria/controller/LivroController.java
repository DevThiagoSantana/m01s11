package tech.devinhouse.livraria.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.devinhouse.livraria.dto.LivroRequest;
import tech.devinhouse.livraria.dto.LivroResponse;
import tech.devinhouse.livraria.model.Livro;
import tech.devinhouse.livraria.service.LivroService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/livros")
@AllArgsConstructor
public class LivroController {

    private ModelMapper mapper;
    private LivroService service;

    @PostMapping
    public ResponseEntity<LivroResponse> criar(@RequestBody @Valid LivroRequest request) {
        Livro livro = mapper.map(request, Livro.class);
        livro = service.salvar(livro);
        LivroResponse resp = mapper.map(livro, LivroResponse.class);
        return ResponseEntity.created(URI.create(resp.getId().toString())).body(resp);
    }

}
