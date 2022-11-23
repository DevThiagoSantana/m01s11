package tech.devinhouse.livraria.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.devinhouse.livraria.exception.RegistroExistenteException;
import tech.devinhouse.livraria.model.Usuario;
import tech.devinhouse.livraria.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOpt = repo.findByEmail(email);
        if (usuarioOpt.isEmpty())
            throw new UsernameNotFoundException("Usuário não encontrado!");
        return usuarioOpt.get();
    }
}
