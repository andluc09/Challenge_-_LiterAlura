package br.com.alura.literatura.repository;

import br.com.alura.literatura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContaining(String title);

    List<Book> findByAuthorId(Long authorId);

    @Query(value = "SELECT * FROM books ORDER BY download_count DESC LIMIT 10", nativeQuery = true)
    List<Book> findTop10ByDownloadCount();

    long countByLanguage(String language);
}
