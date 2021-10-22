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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.EventRepository;

@SpringBootApplication
public class EventplannerApplication {
	private static final Logger log = LoggerFactory.getLogger(EventplannerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EventplannerApplication.class, args);
	}
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Bean
	public CommandLineRunner EventDemo(EventRepository erepo) {
		return (args) -> {
			Date date = new Date();
			System.out.println(date.getTime());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			System.out.println(date);
			System.out.println(sdf.format(date));
			erepo.save(new Event("Event1", "dsdsd", date.toString()));
			
			
			log.info("log indo");
		};
		
	}



}
