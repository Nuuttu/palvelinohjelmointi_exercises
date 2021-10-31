package com.example.eventplanner.web;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@RequestMapping(value="/event/add")
	public String addEvent(Model model) {
		model.addAttribute("event", new Event());
		return "eventadd";
	}
	
	@RequestMapping(value="/event/{id}/edit")
	public String editEvent(@PathVariable("id") Long EventId, Model model) {
		model.addAttribute("event", erepo.findById(EventId));
		return "eventedit";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/event/edit/save")
	public String saveEditEvent(Event event) {
		Event newEvent = erepo.findById(event.getId()).orElse(null);
		newEvent.setTitle(event.getTitle());
		newEvent.setDescription(event.getDescription());
		newEvent.setDatetime(event.getDatetime());
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedUser.getName();
		User cuser = urepo.findByUsername(userName);
		if (newEvent.getTitle() == "" || newEvent.getDatetime() == "" || !newEvent.getOwner().equals(cuser)) {
			return "redirect:/";
		}
		erepo.save(newEvent);
		return "redirect:/index";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/event/save")
	public String saveEvent(Event event) {
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedUser.getName();
		User user = urepo.findByUsername(userName);
		Event newEvent = event;
		newEvent.setOwner(user);
		if (event.getTitle() == "" || event.getDatetime() == "") {
			return "redirect:/";
		}
		erepo.save(newEvent);
		return "redirect:../";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/event/{id}/addmember")
	public String addMember(@RequestBody String formData, @PathVariable("id") Long EventId, HttpServletRequest request) {
		Event newEvent = erepo.findById(EventId).orElse(null);
		//User muser = urepo.finByUserName(formData.)
		// https://www.baeldung.com/spring-request-response-body
		// ASDASDASDASDAS
		// PITÄÄKÖ MUKA TEHÄ OMA LUOKKA hmmhmm
		
		//asdasdasdasd
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedUser.getName();
		User cuser = urepo.findByUsername(userName);
		if (!newEvent.getOwner().equals(cuser)) {
			return "redirect:/";
		}
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
	
	
	@RequestMapping(value = "/signin")
	  public String addUser(Model model) {
	    model.addAttribute("user", new User());
	    return "signinpage";
	  }
	
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
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
	// SAVE EVENT
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/events/save")
	public @ResponseBody String saveEventRest(Event event) {
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedUser.getName();
		User user = urepo.findByUsername(userName);
		Event newEvent = event;
		newEvent.setOwner(user);
		System.out.println(event.getTitle());
		System.out.println(event.getDescription());
		System.out.println(event.getDatetime());
		System.out.println(event.getOwner().getUsername());
		if (event.getTitle() == "" || event.getDatetime() == "") {
			return "redirect:/";
		}
		erepo.save(newEvent);
		return "redirect:../";
	}
	 
}
