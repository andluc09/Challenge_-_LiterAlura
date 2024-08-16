package br.com.alura.literatura.controller;

import br.com.alura.literatura.model.Author;
import br.com.alura.literatura.model.Book;
import br.com.alura.literatura.service.LiteraturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/literatura")
public class LiteraturaController {

    @Autowired
    private LiteraturaService literaturaService;

    @GetMapping("/search/title")
    public List<Book> searchBookByTitle(@RequestParam String title) {
        return literaturaService.searchAndInsertBookByTitle(title);
    }

    @GetMapping("/search/author")
    public List<Book> searchBooksByAuthor(@RequestParam String author) {
        return literaturaService.searchAndInsertBooksByAuthor(author);
    }

    @GetMapping("/books")
    public List<Book> listAllBooks() {
        return literaturaService.listAllBooks();
    }

    @GetMapping("/authors")
    public List<Author> listAllAuthors() {
        return literaturaService.listAllAuthors();
    }

    @GetMapping("/authors/alive")
    public List<Author> listAuthorsAliveInYear(@RequestParam int year) {
        return literaturaService.listAuthorsAliveInYear(year);
    }

    @GetMapping("/books/language")
    public List<Book> listBooksByLanguage(@RequestParam String language) {
        return literaturaService.listBooksByLanguage(language);
    }

    @GetMapping("/books/top")
    public List<Book> listTop10DownloadedBooks() {
        return literaturaService.listTop10DownloadedBooks();
    }
}
