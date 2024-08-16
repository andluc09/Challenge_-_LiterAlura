package br.com.alura.literatura.client;

import br.com.alura.literatura.model.Author;
import br.com.alura.literatura.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public BookClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Book> searchBooks(String query) {
        List<Book> books = new ArrayList<>();
        String url = "https://gutendex.com/books/?search=" + query.replace(" ", "%20");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode rootNode = objectMapper.readTree(response.body());
            JsonNode results = rootNode.get("results");

            if (results != null && results.isArray() && results.size() > 0) {
                for (JsonNode result : results) {
                    Book book = parseBook(result);
                    books.add(book);
                }
            } else {
                System.out.println("\nNenhum livro encontrado para a pesquisa: " + query);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return books;
    }

    private Book parseBook(JsonNode node) {
        Book book = new Book();
        book.setTitle(node.get("title").asText());
        book.setLanguage(node.get("languages").get(0).asText());
        book.setDownloadCount(node.get("download_count").asInt());

        JsonNode authors = node.get("authors");
        if (authors.isArray() && authors.size() > 0) {
            JsonNode authorNode = authors.get(0);
            Author author = new Author();
            author.setName(authorNode.get("name").asText());
            author.setBirthYear(authorNode.get("birth_year").asInt());
            author.setDeathYear(authorNode.get("death_year").asInt());
            book.setAuthor(author);
        }

        return book;
    }
}