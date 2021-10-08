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
import com.example.bookstore.domain.CategoryRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CategoryRepositoryTest {

	@Autowired
    private CategoryRepository repository;

    @Test
    public void findByNameWorks() {
        List<Category> cats= repository.findByName("Thriller");
        assertThat(cats).hasSize(1);
    }

    @Test
    public void createNewCategory() {
    	Category cat = new Category("Thrill");

    	repository.save(cat);
    	assertThat(cat.getCategoryid()).isNotNull();
    }
    
    @Test
    public void deleteCategory() {
    	Category cat = new Category("Thrill");
    	
    	repository.save(cat);
    	assertThat(cat.getCategoryid()).isNotNull();
    	assertThat(repository.findByName("Thrill")).hasSize(1);
    	repository.deleteById(cat.getCategoryid());
    	assertThat(repository.findByName("Thrill")).hasSize(0);
    }

}
