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
import java.util.Date;

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
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CommandLineRunner EventDemo(EventRepository erepo, UserRepository urepo) {
		return (args) -> {
			Date date = new Date();
			System.out.println(date.getTime());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			System.out.println(date);
			System.out.println(sdf.format(date));
			erepo.save(new Event("Event1", "dsdsd", date.toString()));
			erepo.save(new Event("Event2", "This is a event 2", date.toString()));
			
			urepo.save(new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER"));
			urepo.save(new User("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN"));
			
			log.info("log indo");
		};
		
	}



}
