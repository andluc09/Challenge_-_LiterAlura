package br.com.alura.literatura.controller;

import br.com.alura.literatura.service.LiteraturaService;
import br.com.alura.literatura.model.Book;
import br.com.alura.literatura.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LiteraturaController {

    @Autowired
    private LiteraturaService literaturaService;

    @GetMapping("/books")
    public List<Book> getBooksByTitle(@RequestParam String title) {
        return literaturaService.searchBookByTitle(title);
    }

    @GetMapping("/authors")
    public List<Book> getBooksByAuthor(@RequestParam String author) {
        return literaturaService.searchBooksByAuthor(author);
    }

    @GetMapping("/all-books")
    public List<Book> listAllBooks() {
        return literaturaService.listAllBooks();
    }

    @GetMapping("/all-authors")
    public List<Author> listAllAuthors() {
        return literaturaService.listAllAuthors();
    }

    @GetMapping("/authors-alive-in-year")
    public List<Author> listAuthorsAliveInYear(@RequestParam int year) {
        return literaturaService.listAuthorsAliveInYear(year);
    }

    @GetMapping("/books-by-language")
    public List<Book> listBooksByLanguage(@RequestParam String language) {
        return literaturaService.listBooksByLanguage(language);
    }

    @GetMapping("/top-10-downloaded-books")
    public List<Book> listTop10DownloadedBooks() {
        return literaturaService.listTop10DownloadedBooks();
    }
}