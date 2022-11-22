package tech.devinhouse.livraria.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.devinhouse.livraria.exception.RegistroExistenteException;
import tech.devinhouse.livraria.model.Livro;
import tech.devinhouse.livraria.repository.LivroRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LivroService {

    private LivroRepository repo;

    public Livro salvar(Livro livro) {
        Optional<Livro> livroOpt = repo.findByIsbn(livro.getIsbn());
        if (livroOpt.isPresent())
            throw new RegistroExistenteException("Livro", livro.getIsbn());
        livro.setDataInclusao(LocalDateTime.now());
        livro = repo.save(livro);
        return livro;
    }
}
