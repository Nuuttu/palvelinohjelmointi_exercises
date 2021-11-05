package com.example.eventplanner.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventplanner.domain.Comment;
import com.example.eventplanner.domain.CommentRepository;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.EventRepository;
import com.example.eventplanner.domain.User;
import com.example.eventplanner.domain.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class RController {
	
	
	@Autowired
	private EventRepository erepo;
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private CommentRepository crepo;
	
	@Autowired 
    private BCryptPasswordEncoder passwordEncoder;
	
	// REST API
	// USERS
	@RequestMapping(value = "/users", method = RequestMethod.GET)	  
	  public @ResponseBody List<User> userListRest() {
	    return (List<User>) urepo.findAll();
	  }
	// By id
	 @RequestMapping(value="/users/{id}", method = RequestMethod.GET)
	    public @ResponseBody Optional<User> findUserRest(@PathVariable("id") Long userId) {	
	    	return urepo.findById(userId);
	 	}  
	// EVENTS  
	@RequestMapping(value = "/events", method = RequestMethod.GET)	  
	  public @ResponseBody List<Event> eventListRest() {
	    return (List<Event>) erepo.findAll();
	  }
	// BY ID
	@RequestMapping(value="/events/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Event> findEventRest(@PathVariable("id") Long eventId) {	
    	return erepo.findById(eventId);
 	}
	// COMMENTS
	@RequestMapping(value = "/comments", method = RequestMethod.GET)	  
	public @ResponseBody List<Comment> commentListRest() {
	  return (List<Comment>) crepo.findAll();
	}
	// BY ID
	@RequestMapping(value="/comments/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Comment> findCommentRest(@PathVariable("id") Long cId) {	
		return crepo.findById(cId);
 	}
	// SAVE EVENT
	@CrossOrigin
	@RequestMapping(value="/events/save", method = RequestMethod.POST)
	public @ResponseBody Event saveEventRest(Event event) {
		/*
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedUser.getName();
		User user = urepo.findByUsername(userName);
		Event newEvent = event;
		newEvent.setOwner(user);
		System.out.println(event.getTitle());
		System.out.println(event.getDescription());
		System.out.println(event.getDatetime());
		System.out.println(event.getOwner().getUsername());
		if (event.getTitle() == "" || event.getDatetime() == "" || event.getTitle() == null || event.getDatetime() == null) {
			return newEvent;
		}
		erepo.save(newEvent);
		*/
		return event;
	}
	
	@RequestMapping(value="/asd", method = RequestMethod.DELETE)
	public void del(@PathVariable(required = false) Long id) {
		crepo.deleteAll();
	}
	// ADD USER
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public @ResponseBody String addUser(@RequestBody User data, Model model) {
		System.out.println(data);
		User nuser = new User(data.getUsername(), data.getPasswordHash(), data.getRole() );
		final String encodedPassword = passwordEncoder.encode(nuser.getPasswordHash());
		nuser.setPasswordHash(encodedPassword);
		nuser.setRole("USER");
	    urepo.save(nuser);
	    return nuser.toString();
	}
	
	@RequestMapping(value="/g", method=RequestMethod.POST)
	public Comment greeting(@RequestParam(required = false, defaultValue = "World") String name) {
		System.out.println("==== get greeting ====");
		return new Comment(name);
	}
	
}
