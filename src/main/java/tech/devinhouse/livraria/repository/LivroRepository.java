package tech.devinhouse.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.devinhouse.livraria.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
}
