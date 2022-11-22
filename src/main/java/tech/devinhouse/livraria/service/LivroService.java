package tech.devinhouse.livraria.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tech.devinhouse.livraria.exception.RegistroExistenteException;
import tech.devinhouse.livraria.exception.RegistroNaoEncontradoException;
import tech.devinhouse.livraria.model.Livro;
import tech.devinhouse.livraria.repository.LivroRepository;

import java.time.LocalDateTime;
import java.util.List;
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

    public List<Livro> consultar() {
        return repo.findAll();
    }

    public Livro consultar(Integer id) {
        Optional<Livro> livroOpt = repo.findById(id);
        if (livroOpt.isEmpty())
            throw new RegistroNaoEncontradoException("Livro", id);
        Livro livro = livroOpt.get();
        return livro;
    }

    public Livro atualizar(Livro livroAtualizado) {
        Optional<Livro> livroOpt = repo.findById(livroAtualizado.getId());
        if (livroOpt.isEmpty())
            throw new RegistroNaoEncontradoException("Livro", livroAtualizado.getId());
        Livro livroBD = livroOpt.get();
        livroBD.setTitulo( livroAtualizado.getTitulo() );
        livroBD.setIdioma( livroAtualizado.getIdioma() );
        livroBD.setNroDePaginas( livroAtualizado.getNroDePaginas() );
        livroAtualizado = repo.save(livroBD);
        return livroAtualizado;
    }

    public void excluir(Integer id) {
        boolean existe = repo.existsById(id);
        if (!existe)
            throw new RegistroNaoEncontradoException("Livro", id);
        repo.deleteById(id);
    }
}
