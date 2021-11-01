package com.example.eventplanner.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

	List<Comment> findByEvent(Event event);
	
}
