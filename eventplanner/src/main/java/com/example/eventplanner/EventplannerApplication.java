package com.example.eventplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.eventplanner.domain.CommentRepository;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.EventRepository;
import com.example.eventplanner.domain.User;
import com.example.eventplanner.domain.UserRepository;

@SpringBootApplication
public class EventplannerApplication {
	private static final Logger log = LoggerFactory.getLogger(EventplannerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EventplannerApplication.class, args);
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CommandLineRunner EventDemo(EventRepository erepo, UserRepository urepo, CommentRepository crepo) {
		return (args) -> {
			
			User user = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			urepo.save(user);
			User admin = new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN");
			urepo.save(admin);
			
			Date date = new Date();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

			List<User> e1members = new ArrayList<User>();
			e1members.add(user);
			List<User> e2members = new ArrayList<User>();
			e2members.add(user);
			e2members.add(admin);
			
			Event event1 = new Event("Event1", "dsdsd", formatter.format(date), urepo.findByUsername("user"), e1members);
			erepo.save(event1);
			Event event2 = new Event("Event2", "This is a event 2", formatter.format(date), urepo.findByUsername("user"), e2members);
			erepo.save(event2);
			
			log.info("log indo");
		};
	}
}
