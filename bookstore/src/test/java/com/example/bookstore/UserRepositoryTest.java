package com.example.bookstore;


import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;

import com.example.bookstore.domain.User;
import com.example.bookstore.domain.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {
	@Autowired
    private UserRepository repository;

    @Test
    public void findByUsernameWorks() {
        User test = repository.findByUsername("admin");
        assertThat(test.getRole()).isEqualTo("ADMIN");    
    }
    
    @Test
    public void createNewUser() {
    	User user1 = new User("user2", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
    	repository.save(user1);
    	assertThat(user1.getId()).isNotNull();
    }
    
    
    @Test
    public void deleteUser() {
    	User user1 = new User("user2", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
    	repository.save(user1);
    	assertThat(user1.getId()).isNotNull();
    	repository.deleteById(user1.getId());
    	assertThat(repository.findByUsername(user1.getUsername())).isEqualTo(null);
    }
	
}
