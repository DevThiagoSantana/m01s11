package tech.devinhouse.livraria.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.devinhouse.livraria.dto.LoginRequest;
import tech.devinhouse.livraria.dto.LoginResponse;
import tech.devinhouse.livraria.model.Usuario;
import tech.devinhouse.livraria.service.TokenService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/autenticacao")
@AllArgsConstructor
public class AutenticacaoControler {

    private AuthenticationManager authManager;
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        UsernamePasswordAuthenticationToken credenciais = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha());
        Authentication autenticacao = authManager.authenticate(credenciais);
        Usuario usuario = (Usuario) autenticacao.getPrincipal();
        String accessToken = tokenService.gerarAccessToken(usuario);
        LoginResponse resp = new LoginResponse(usuario.getEmail(), accessToken);
        return ResponseEntity.ok(resp);
    }

}
