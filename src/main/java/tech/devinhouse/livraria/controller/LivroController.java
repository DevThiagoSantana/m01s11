package tech.devinhouse.livraria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.devinhouse.livraria.dto.ErroResponse;
import tech.devinhouse.livraria.dto.LivroRequest;
import tech.devinhouse.livraria.dto.LivroResponse;
import tech.devinhouse.livraria.model.Livro;
import tech.devinhouse.livraria.service.LivroService;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/livros")
@AllArgsConstructor
public class LivroController {

    private static Logger logger = LoggerFactory.getLogger(LivroController.class);

    private ModelMapper mapper;
    private LivroService service;

    @Operation(summary = "Serviço de inserção de livros", description = "Insere livros no servidor para ficar disponível para acesso dos usuários")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Livro cadastrado com sucesso",
                            content = { @Content(schema = @Schema(implementation = LivroResponse.class)) }),
                    @ApiResponse(responseCode = "409", description = "Livro com ISBN já cadastrado",
                            content = { @Content(schema = @Schema(implementation = ErroResponse.class)) }),
                    @ApiResponse(responseCode = "400", description = "Requisição com dados inválidos de livros",
                            content = { @Content(schema = @Schema(implementation = ErroResponse.class)) }
                    )
            }
    )
    @PostMapping
//    @RolesAllowed({"ROLE_FUNCIONARIO", "ROLE_ADMIN"})
    public ResponseEntity<LivroResponse> criar(@RequestBody @Valid LivroRequest request) {
        Livro livro = mapper.map(request, Livro.class);
        livro = service.salvar(livro);
        LivroResponse resp = mapper.map(livro, LivroResponse.class);
        return ResponseEntity.created(URI.create(resp.getId().toString())).body(resp);
    }

    @Operation(summary = "Serviço de consulta de todos os livros cadastrados", description = "Consulta geral de livros")
    @GetMapping
//    @RolesAllowed({"ROLE_FUNCIONARIO", "ROLE_ADMIN", "ROLE_LEITOR"})
    public ResponseEntity<List<LivroResponse>> listar() {
        List<Livro> livros = service.consultar();
        List<LivroResponse> resp = new ArrayList<>();
        for(Livro livro: livros) {
            LivroResponse livroResp = mapper.map(livro, LivroResponse.class);
            resp.add(livroResp);
        }
        return ResponseEntity.ok(resp);
    }

    @GetMapping("{id}")
    public ResponseEntity<LivroResponse> consultarPeloId(@PathVariable("id") Integer id) {
        Livro livro = service.consultar(id);
        LivroResponse resp = mapper.map(livro, LivroResponse.class);
        return ResponseEntity.ok(resp);
    }

    @PutMapping("{id}")
    public ResponseEntity<LivroResponse> atualizar(@PathVariable("id") Integer id, @RequestBody @Valid LivroRequest request) {
        Livro livro = mapper.map(request, Livro.class);
        livro.setId(id);
        livro = service.atualizar(livro);
        LivroResponse resp = mapper.map(livro, LivroResponse.class);
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Integer id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
