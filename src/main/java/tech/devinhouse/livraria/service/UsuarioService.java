package tech.devinhouse.livraria.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.devinhouse.livraria.exception.RegistroExistenteException;
import tech.devinhouse.livraria.model.Usuario;
import tech.devinhouse.livraria.repository.UsuarioRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository repo;
    private PasswordEncoder passwordEncoder;

    public Usuario criar(Usuario usuario) {
        boolean emailExistente = repo.existsUsuarioByEmail(usuario.getEmail());
        if (emailExistente)
            throw new RegistroExistenteException("Usuario", usuario.getEmail());
        String senhaCodificada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada);
        usuario = repo.save(usuario);
        return usuario;
    }

    public List<Usuario> listar() {
        return repo.findAll();
    }

}
