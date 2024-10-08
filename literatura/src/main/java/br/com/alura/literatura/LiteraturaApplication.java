package br.com.alura.literatura;

import br.com.alura.literatura.service.LiteraturaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
@EntityScan("br.com.alura.literatura.model")
public class LiteraturaApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(LiteraturaService literaturaService) {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			int option = -1;

			while (option != 0) {
				System.out.println("————————————————————————————————————————————————————————");
				System.out.println("ㅤㅤㅤㅤ🏛️ Seja bem-vindo(a) ao Acervo Literário 😃");
				System.out.println("————————————————————————————————————————————————————————");
				System.out.println("1 - Busca de livro por título");
				System.out.println("2 - Busca de livro por autor");
				System.out.println("3 - Listagem de todos os livros registrados");
				System.out.println("4 - Listagem de autores registrados");
				System.out.println("5 - Listar autores vivos em determinado ano");
				System.out.println("6 - Listar a quantidade de livros em um determinado idioma");
				System.out.println("7 - Top 10 livros mais baixados");
				System.out.println("0 - Sair");
				System.out.println("————————————————————————————————————————————————————————");
				System.out.print("\nEscolha uma das opções: ");

				try {
					option = Integer.parseInt(scanner.nextLine());
				} catch (NumberFormatException e) {
					System.out.println("\nPor favor, insira um número válido.");
					continue;
				}

				switch (option) {
					case 1:
						System.out.print("\nDigite o título do livro: ");
						String title = scanner.nextLine();
						literaturaService.searchAndInsertBookByTitle(title);
						break;
					case 2:
						System.out.print("\nDigite o nome do autor: ");
						String author = scanner.nextLine();
						literaturaService.searchAndInsertBooksByAuthor(author);
						break;
					case 3:
						literaturaService.listAllBooks();
						break;
					case 4:
						literaturaService.listAllAuthors();
						break;
					case 5:
						System.out.print("\nDigite o ano para verificar autores vivos: ");
						int year = Integer.parseInt(scanner.nextLine());
						literaturaService.listAuthorsAliveInYear(year);
						break;
					case 6:
						System.out.println("\nInsira um idioma para realizar a busca:\n");
						System.out.println("es - espanhol");
						System.out.println("en - inglês");
						System.out.println("fr - francês");
						System.out.println("pt - português\n");

						String language;
						while (true) {
							language = scanner.nextLine().trim().toLowerCase();
							if (language.equals("es") || language.equals("en") || language.equals("fr") || language.equals("pt")) {
								break;
							} else {
								System.out.println("\nSigla inválida! Por favor, insira uma sigla válida (es, en, fr, pt):\n");
							}
						}

						literaturaService.listBooksByLanguage(language);
						break;
					case 7:
						literaturaService.listTop10DownloadedBooks();
						break;
					case 0:
						System.out.println("\nSaindo...");
						System.exit(0);
						break;
					default:
						System.out.println("\nOpção inválida! Tente novamente..");
				}
			}
		};
	}
}
