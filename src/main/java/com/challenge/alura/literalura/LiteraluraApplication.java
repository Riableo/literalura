package com.challenge.alura.literalura;

import com.challenge.alura.literalura.principal.Principal;
import com.challenge.alura.literalura.repository.IAuthorRepository;
import com.challenge.alura.literalura.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private IBookRepository repository;
	@Autowired
	private IAuthorRepository repoAuthor;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, repoAuthor);
		principal.showMenu();
	}
}
