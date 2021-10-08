package com.example.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.Category;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {
	@Autowired
    private BookRepository repository;

    @Test
    public void findByTitleShouldReturnBook() {
        List<Book> books= repository.findByTitle("Book2");
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("Kateson");
    }
    
    @Test
    public void createNewBook() {
    	Book book = new Book("BookThree", "Ruuu", 1992, 29299292, 20.2, new Category("Thrill"));
    	//Book book = new Book("Book2", "Kateson", 1992, 29299292, 20.2, catrepo.findByName("Thriller").get(0));
    	repository.save(book);
    	assertThat(book.getId()).isNotNull();
    }
    
    
    @Test
    public void deleteBook() {
    	Book book = new Book("BookFour", "Ruuu", 1992, 29299292, 20.2, new Category("Thrill"));
    	//Book book = new Book("Book2", "Kateson", 1992, 29299292, 20.2, catrepo.findByName("Thriller").get(0));
    	repository.save(book);
    	assertThat(book.getId()).isNotNull();
    	repository.deleteById(book.getId());
    	assertThat(repository.findByTitle("BookFour")).hasSize(0);
    }
    
}
