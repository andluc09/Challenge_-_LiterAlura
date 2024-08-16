package br.com.alura.literatura.service;

import br.com.alura.literatura.client.BookClient;
import br.com.alura.literatura.model.Author;
import br.com.alura.literatura.model.Book;
import br.com.alura.literatura.repository.AuthorRepository;
import br.com.alura.literatura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LiteraturaService {

    @Autowired
    private BookClient bookClient;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Transactional
    public void insertBook(String title, String authorName, String language, Long downloadCount, Integer authorBirthYear, Integer authorDeathYear) {
        Optional<Author> existingAuthor = authorRepository.findByName(authorName);
        Author author;
        if (existingAuthor.isPresent()) {
            author = existingAuthor.get();
            System.out.println("\nEste autor já se encontra salvo no banco de dados: " + author.getId() + "\n");
        } else {
            Author newAuthor = new Author();
            newAuthor.setName(authorName);
            newAuthor.setBirthYear(authorBirthYear);
            newAuthor.setDeathYear(authorDeathYear);
            author = authorRepository.save(newAuthor);
            System.out.println("\nNovo autor salvo: " + author.getId() + "\n");
        }

        Optional<Book> existingBook = bookRepository.findByTitle(title);
        if (existingBook.isEmpty()) {
            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setLanguage(language);
            book.setDownloadCount(Math.toIntExact(downloadCount));
            bookRepository.save(book);
            System.out.println("\nLivro salvo: " + book.getId() + "\n");
        } else {
            System.out.println("\nO livro '" + title + "' já existe no banco de dados.\n");
        }
    }

    @Transactional
    public void insertAuthor(String name, Integer birthYear, Integer deathYear) {
        Optional<Author> existingAuthor = authorRepository.findByName(name);
        if (existingAuthor.isEmpty()) {
            Author author = new Author();
            author.setName(name);
            author.setBirthYear(birthYear);
            author.setDeathYear(deathYear);
            authorRepository.save(author);
            System.out.println("\nAutor salvo: " + author.getId() + "\n");
        } else {
            System.out.println("\nO autor '" + name + "' já existe no banco de dados.\n");
        }
    }

    public List<Book> searchAndInsertBookByTitle(String title) {
        List<Book> books = bookClient.searchBooks(title);
        books.forEach(book -> {
            insertBook(
                    book.getTitle(),
                    book.getAuthor().getName(),
                    book.getLanguage(),
                    book.getDownloadCount().longValue(),
                    book.getAuthor().getBirthYear(),
                    book.getAuthor().getDeathYear()
            );
            System.out.println("\n----------- 📚 LIVRO -----------" );
            System.out.println("Título: " + book.getTitle());
            System.out.println("Autor: " + book.getAuthor().getName());
            System.out.println("Idioma: " + book.getLanguage());
            System.out.println("Downloads: " + book.getDownloadCount());
            System.out.println("--------------------------------\n");
        });
        return books;
    }

    public List<Book> searchAndInsertBooksByAuthor(String authorName) {
        List<Book> books = bookClient.searchBooks(authorName);
        books.forEach(book -> {
            if (book.getAuthor().getName().equalsIgnoreCase(authorName)) {
                insertBook(
                        book.getTitle(),
                        book.getAuthor().getName(),
                        book.getLanguage(),
                        book.getDownloadCount().longValue(),
                        book.getAuthor().getBirthYear(),
                        book.getAuthor().getDeathYear()
                );
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
            System.out.println("Autor: " + book.getAuthor().getName());
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
        List<Author> authors = authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqualOrDeathYearIsNull(year, year);
        if (authors.isEmpty()) {
            System.out.println("\nNão há autores vivos a partir do ano " + year + " no banco de dados.\n");
        } else {
            authors.forEach(author -> {
                System.out.println("\n----------- 🖊️ AUTOR -----------");
                System.out.println("Nome: " + author.getName());
                System.out.println("Ano de Nascimento: " + author.getBirthYear());
                System.out.println("Ano de Falecimento: " + author.getDeathYear());
                System.out.println("--------------------------------\n");
            });
        }
        return authors;
    }

    public List<Book> listBooksByLanguage(String language) {
        long count = bookRepository.countByLanguage(language);
        System.out.println("\n--------------------------------");
        System.out.println("Quantidade de livros em " + language + ": " + count);
        System.out.println("--------------------------------\n");

        List<Book> books = bookRepository.findAll();
        books.forEach(book -> {
            if (book.getLanguage().equalsIgnoreCase(language)) {
                System.out.println("\n----------- 📚 LIVRO -----------" );
                System.out.println("Título: " + book.getTitle());
                System.out.println("Autor: " + book.getAuthor().getName());
                System.out.println("Idioma: " + book.getLanguage());
                System.out.println("Downloads: " + book.getDownloadCount());
                System.out.println("--------------------------------\n");
            }
        });
        return books;
    }

    public List<Book> listTop10DownloadedBooks() {
        List<Book> books = bookRepository.findTop10ByOrderByDownloadCountDesc();
        books.forEach(book -> {
            System.out.println("\n----------- 📚 LIVRO -----------" );
            System.out.println("Título: " + book.getTitle());
            System.out.println("Autor: " + book.getAuthor().getName());
            System.out.println("Idioma: " + book.getLanguage());
            System.out.println("Número de downloads: " + book.getDownloadCount());
            System.out.println("--------------------------------\n");
        });
        return books;
    }
}
