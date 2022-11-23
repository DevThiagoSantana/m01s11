package tech.devinhouse.livraria.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    public String extrairToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new IllegalArgumentException("Invalid Headers");
        // Bearer TOKEN_JWT&*%Bbjbjbcbvmxzvb,xzv
//        String token = authorizationHeader.substring("Bearer ".length());
        String[] split = authorizationHeader.split(" ");
        String token = split[1];
        return token;
    }

    public DecodedJWT decodificarToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(segredo.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT;
    }
}
