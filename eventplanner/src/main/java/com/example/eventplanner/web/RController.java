package com.example.eventplanner.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	@RequestMapping(value = "/")
	public @ResponseBody String RestIndex() {
		return "rest: /users /events /comments";
	}
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
	// ADD USER
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<HttpStatus> addUser(@RequestBody User data) {
		System.out.println(data);
		User nuser = new User(data.getUsername(), data.getPasswordHash(), data.getRole());
		final String encodedPassword = passwordEncoder.encode(nuser.getPasswordHash());
		nuser.setPasswordHash(encodedPassword);
		nuser.setRole("USER");
	    urepo.save(nuser);
	    return ResponseEntity.ok(HttpStatus.OK);
	}
	
	 
	// EVENTS  
	@RequestMapping(value = "/events", method = RequestMethod.GET)	  
	  public @ResponseBody List<Event> eventListRest() {
	    return (List<Event>) erepo.findAll();
	  }
	// EVENT - BY ID
	@RequestMapping(value="/events/{id}", method = RequestMethod.GET)
    public @ResponseBody Optional<Event> findEventRest(@PathVariable("id") Long eventId) {	
    	return erepo.findById(eventId);
 	}
	// EVENT - ADD
	@RequestMapping(value="/events/add", method = RequestMethod.POST)
	public ResponseEntity<Event> addEventRest(@RequestBody Event data) {
		Event cevent = new Event(
				data.getTitle(), 
				data.getDescription(), 
				data.getDatetime(), 
				urepo.findByUsername(data.getOwner().getUsername()));
		Event savedEvent = erepo.save(cevent);
		return new ResponseEntity<Event>(savedEvent, HttpStatus.OK);
	}
	// EVENT - EDIT
	@RequestMapping(value="/events/{id}/edit", method = RequestMethod.POST)
	public ResponseEntity<Event> editEventRest(@PathVariable("id") Long eid, @RequestBody Event data) {
		Event eevent = erepo.findById(eid).orElse(null);
		eevent.setTitle(data.getTitle());
		eevent.setDescription(data.getDescription());
		eevent.setDatetime(data.getDatetime());
		erepo.save(eevent);
		return new ResponseEntity<Event>(eevent, HttpStatus.OK);
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
	
	
	@RequestMapping(value="/asd", method = RequestMethod.DELETE)
	public void del(@PathVariable(required = false) Long id) {
		crepo.deleteAll();
	}

	@RequestMapping(value="/g", method=RequestMethod.POST)
	public Comment greeting(@RequestParam(required = false, defaultValue = "World") String name) {
		System.out.println("==== get greeting ====");
		return new Comment(name);
	}
	
}
