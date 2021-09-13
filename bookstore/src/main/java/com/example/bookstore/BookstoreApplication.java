package com.example.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Category;
import com.example.bookstore.domain.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {
	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner studentDemo(BookRepository bookrepo, CategoryRepository catrepo) {
		return (args) -> {
			log.info("save a couple of categories and books");
			catrepo.save(new Category("Thriller"));
			catrepo.save(new Category("Fantasy"));
			catrepo.save(new Category("Scifi"));
			
			bookrepo.save(new Book("Book2", "Kateson", 1992, 29299292, 20.2, catrepo.findByName("Thriller").get(0)));
			bookrepo.save(new Book("Book1", "Johnson", 1990, 1293129393, 20.1, catrepo.findByName("Scifi").get(0)));
			
			
			log.info("fetch all books");
			for (Book book: bookrepo.findAll()) {
				log.info(book.toString());
			}

		};
	}
}



