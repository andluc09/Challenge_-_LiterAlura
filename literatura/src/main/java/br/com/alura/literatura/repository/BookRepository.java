package br.com.alura.literatura.repository;

import br.com.alura.literatura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    List<Book> findTop10ByOrderByDownloadCountDesc();
    long countByLanguage(String language);
}
