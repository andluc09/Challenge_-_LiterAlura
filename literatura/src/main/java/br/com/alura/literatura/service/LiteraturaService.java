package br.com.alura.literatura.service;

import br.com.alura.literatura.client.BookClient;
import br.com.alura.literatura.model.Author;
import br.com.alura.literatura.model.Book;
import br.com.alura.literatura.repository.AuthorRepository;
import br.com.alura.literatura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LiteraturaService {

    @Autowired
    private BookClient bookClient;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    // Busca livro por título usando a API
    public List<Book> searchBookByTitle(String title) {
        List<Book> books = bookClient.searchBooks(title);
        books.forEach(book -> {
            System.out.println("\n----------- 📚 LIVRO -----------" );
            System.out.println("Título: " + book.getTitle());
            System.out.println("Autor: " + book.getAuthor());
            System.out.println("Idioma: " + book.getLanguage());
            System.out.println("Downloads: " + book.getDownloadCount());
            System.out.println("--------------------------------\n");
        });
        return books;
    }

    // Busca livros por autor usando a API
    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> books = bookClient.searchBooks(authorName);
        books.forEach(book -> {
            if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
                System.out.println("\n----------- 📚 LIVRO -----------" );
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autor: " + book.getAuthor());
                System.out.println("Idioma: " + book.getLanguage());
                System.out.println("Downloads: " + book.getDownloadCount());
                System.out.println("--------------------------------\n");
            }
        });
        return books;
    }

    // Listar todos os livros registrados no banco de dados
    public List<Book> listAllBooks() {
        List<Book> books = bookRepository.findAll();
        books.forEach(book -> {
            System.out.println("\n----------- 📚 LIVRO -----------" );
            System.out.println("Título: " + book.getTitle());
            System.out.println("Autor: " + book.getAuthor());
            System.out.println("Idioma: " + book.getLanguage());
            System.out.println("Downloads: " + book.getDownloadCount());
            System.out.println("--------------------------------\n");
        });
        return books;
    }

    // Listar todos os autores registrados no banco de dados
    public List<Author> listAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        authors.forEach(author -> {
            System.out.println("\n----------- 🖊️ AUTOR -----------" );
            System.out.println("Nome: " + author.getName());
            System.out.println("Ano de Nascimento: " + author.getBirthYear());
            System.out.println("Ano de Falecimento: " + author.getDeathYear());
            System.out.println("--------------------------------\n");
        });
        return authors;
    }

    // Listar autores vivos em um determinado ano registrados no banco de dados
    public List<Author> listAuthorsAliveInYear(int year) {
        List<Author> authors = authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqualOrDeathYearIsNull(year, year);
        authors.forEach(author -> {
            System.out.println("\n----------- 🖊️ AUTOR -----------" );
            System.out.println("Nome: " + author.getName());
            System.out.println("Ano de Nascimento: " + author.getBirthYear());
            System.out.println("Ano de Falecimento: " + author.getDeathYear());
            System.out.println("--------------------------------\n");
        });
        return authors;
    }

    // Listar a quantidade de livros em um determinado idioma registrados no banco de dados
    public List<Book> listBooksByLanguage(String language) {
        long count = bookRepository.countByLanguage(language);
        System.out.println("\n--------------------------------");
        System.out.println("Quantidade de livros em " + language + ": " + count);
        System.out.println("----------- 📚 LIVRO -----------" );

        List<Book> books = bookRepository.findAll();
        books.forEach(book -> {
            if (book.getLanguage().equalsIgnoreCase(language)) {
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autor: " + book.getAuthor());
                System.out.println("Idioma: " + book.getLanguage());
                System.out.println("Downloads: " + book.getDownloadCount());
                System.out.println("--------------------------------\n");
            }
        });
        return books;
    }

    // Listar os 10 livros mais baixados registrados no banco de dados
    public List<Book> listTop10DownloadedBooks() {
        List<Book> books = bookRepository.findTop10ByDownloadCount();
        books.forEach(book -> {
            System.out.println("\n----------- 📚 LIVRO -----------" );
            System.out.println("Título: " + book.getTitle());
            System.out.println("Autor: " + book.getAuthor());
            System.out.println("Idioma: " + book.getLanguage());
            System.out.println("Número de downloads: " + book.getDownloadCount());
            System.out.println("--------------------------------\n");
        });
        return books;
    }
}
