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

    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> books = bookClient.searchBooks(authorName);
        books.forEach(book -> {
            if (book.getAuthor().equalsIgnoreCase(authorName)) {
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

    public List<Author> listAuthorsAliveInYear(int year) {
        List<Author> authors = authorRepository.findAll();
        authors.forEach(author -> {
            if (author.getBirthYear() <= year && (author.getDeathYear() == null || author.getDeathYear() >= year)) {
                System.out.println("\n----------- 🖊️ AUTOR -----------" );
                System.out.println("Nome: " + author.getName());
                System.out.println("Ano de Nascimento: " + author.getBirthYear());
                System.out.println("Ano de Falecimento: " + author.getDeathYear());
                System.out.println("--------------------------------\n");
            }
        });
        return authors;
    }

    public List<Book> listBooksByLanguage(String language) {
        List<Book> books = bookRepository.findAll();
        long count = books.stream().filter(book -> book.getLanguage().equalsIgnoreCase(language)).count();
        System.out.println("\n--------------------------------");
        System.out.println("Quantidade de livros em " + language + ": " + count);
        System.out.println("----------- 📚 LIVRO -----------" );
        books.forEach(book -> {
            System.out.println("Título: " + book.getTitle());
            System.out.println("Autor: " + book.getAuthor());
            System.out.println("Idioma: " + book.getLanguage());
            System.out.println("Downloads: " + book.getDownloadCount());
            System.out.println("--------------------------------\n");
        });
        return books;
    }

    public List<Book> listTop10DownloadedBooks() {
        List<Book> books = bookRepository.findAll();
        books.stream()
                .sorted((b1, b2) -> Integer.compare(b2.getDownloadCount(), b1.getDownloadCount()))
                .limit(10)
                .forEach(book -> {
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