package com.example.eventplanner.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.EventRepository;
import com.example.eventplanner.domain.User;
import com.example.eventplanner.domain.UserRepository;

@Controller
public class EventplannerController {
	
	@Autowired
	private EventRepository erepo;
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired 
    private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping(value={"/", "/index"})
	public String index(Model model) {
		model.addAttribute("events", erepo.findAll());
		return "index";
	}
	
	@RequestMapping(value="/logoutpage")
	public String logout() {
		return "logoutpage";
	}
	
	@RequestMapping(value="/event/{id}", method = RequestMethod.GET)
	public String eventPageSingle(@PathVariable("id") Long eventId, Model model) {

		model.addAttribute("event", erepo.findById(eventId));
		model.addAttribute("events", erepo.findAll());
		return "eventpagesingle";
	}
	
	@RequestMapping(value = "/signin")
	  public String addUser(Model model) {
	    model.addAttribute("user", new User());
	    return "signinpage";
	  }
	
	@RequestMapping(value = "/save/user", method = RequestMethod.POST)
	  public String saveUser(User user) {
		// urepo.save(new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER"));
		final String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
		user.setPasswordHash(encodedPassword);
		user.setRole("USER");
		System.out.println(user.getPasswordHash());
	    urepo.save(user);
	    return "redirect:../login";
	  }
	

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
	  
}
