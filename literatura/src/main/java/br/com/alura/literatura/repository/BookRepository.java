package br.com.alura.literatura.repository;

import br.com.alura.literatura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByLanguageIgnoreCase(String language);
    List<Book> findTop10ByOrderByDownloadCountDesc();
}
