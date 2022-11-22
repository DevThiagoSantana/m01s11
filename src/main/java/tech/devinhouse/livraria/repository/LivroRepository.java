package tech.devinhouse.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.devinhouse.livraria.model.Livro;

import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Integer> {

    Optional<Livro> findByIsbn(String isbn);

}
