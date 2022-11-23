package tech.devinhouse.livraria.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.devinhouse.livraria.model.Role;
import tech.devinhouse.livraria.model.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TokenService {

    @Value("${segredo}")
    private String segredo;

    public String gerarAccessToken(Usuario usuario) {
        List<String> rolesStr = new ArrayList<>();
        for (Role role : usuario.getRoles()) {
            rolesStr.add( role.name() );
        }
        Algorithm algoritmo = Algorithm.HMAC256(segredo.getBytes());
        String token = JWT.create()
            .withSubject(usuario.getEmail()) // identificador do portador do token, ou seja, identificador do usuario
            .withIssuer("Livraria-API")  // emissor do token
            .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))  // expires in 5 min
            .withClaim("roles", rolesStr)
            .sign(algoritmo);
        return token;
    }

}
