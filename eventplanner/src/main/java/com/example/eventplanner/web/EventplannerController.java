package com.example.eventplanner.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.example.eventplanner.domain.Comment;
import com.example.eventplanner.domain.CommentRepository;
import com.example.eventplanner.domain.Event;
import com.example.eventplanner.domain.EventRepository;
import com.example.eventplanner.domain.Member;
import com.example.eventplanner.domain.User;
import com.example.eventplanner.domain.UserRepository;

@Controller
public class EventplannerController {
	
	@Autowired
	private EventRepository erepo;
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private CommentRepository crepo;
	
	@Autowired 
    private BCryptPasswordEncoder passwordEncoder;
	
	private boolean hasPermission(Event event) {
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedUser.getName();
		User cuser = urepo.findByUsername(userName);
		if (cuser == event.getOwner()) {
			return true;
		}
		return false;
	}
	
	// INDEX
	@RequestMapping(value={"/", "/index"})
	public String index(Model model) {
		model.addAttribute("events", erepo.findAll());
		return "index";
	}
	// LOGOUT
	@RequestMapping(value="/logoutpage")
	public String logout() {
		return "logoutpage";
	}
	// EVENT PAGE ID
	@RequestMapping(value="/event/{id}", method = RequestMethod.GET)
	public String eventPageSingle(@PathVariable("id") Long eventId, Model model) {
		model.addAttribute("event", erepo.findById(eventId));
		model.addAttribute("events", erepo.findAll());
		model.addAttribute("member", new Member());
		model.addAttribute("comment", new Comment());
		model.addAttribute("comments", crepo.findByEvent(erepo.findById(eventId).orElse(null)));
		return "eventpagesingle";
	}
	// ADD EVENT
	@RequestMapping(value="/event/add")
	public String addEvent(Model model) {
		model.addAttribute("event", new Event());
		return "eventadd";
	}
	// DELETE EVENT
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/event/{id}/delete")
	public String removeEvent(@PathVariable("id") Long eventId) {
		Event dEvent = erepo.findById(eventId).orElse(null);
		if (hasPermission(dEvent)) {
			erepo.delete(dEvent);
		}
		return "redirect:/";
	}
	// EDIT EVENT PAGE
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/event/{id}/edit")
	public String editEvent(@PathVariable("id") Long EventId, Model model) {
		model.addAttribute("event", erepo.findById(EventId));
		return "eventedit";
	}
	// EDIT EVENT SAVE
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
		if (newEvent.getTitle() == "" || newEvent.getTitle() == null || newEvent.getDatetime() == "" || newEvent.getDatetime() == null || !newEvent.getOwner().equals(cuser)) {
			return "redirect:/";
		}
		erepo.save(newEvent);
		return "redirect:/index";
	}
	// SAVE EVENT
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/event/save")
	public String saveEvent(Event event) {
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedUser.getName();
		User user = urepo.findByUsername(userName);
		Event newEvent = event;
		newEvent.setOwner(user);
		if (event.getTitle() == "" || event.getDatetime() == "" || user == null) {
			return "redirect:/";
		}
		erepo.save(newEvent);
		return "redirect:../";
	}
	// ADD MEMBER
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/event/{id}/addmember")
	public String addMember(Member ame, @PathVariable("id") Long EventId, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		Event newEvent = erepo.findById(EventId).orElse(null);;
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedUser.getName();
		User cuser = urepo.findByUsername(userName);
		if (!newEvent.getOwner().equals(cuser)) {
			return "redirect:" + referer;
		}
		User newMember = urepo.findByUsername(ame.getAddmember());
		if (newMember == null) {
			return "redirect:" + referer;
		}
		if (newEvent.getMembers().contains(newMember)) {
			return "redirect:" + referer;
		}
		newEvent.addMember(newMember);
		erepo.save(newEvent);
		return "redirect:" + referer;
	}
	// DELETE MEMBER
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/event/{eid}/removemember/{mid}")
	public String removeMember(@PathVariable("eid") Long eventId, @PathVariable("mid") Long memberId) {
		Event dEvent = erepo.findById(eventId).orElse(null);
		User dMember = urepo.findById(memberId).orElse(null);
		dEvent.removeMember(dMember);
		if (hasPermission(dEvent)) {
			erepo.save(dEvent);
		}
		return "redirect:../";
	}
	// ADD COMMENT
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/event/{id}/addcomment")
	public String addComment(Comment newComment, @PathVariable("id") Long eventId, HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		Event newEvent = erepo.findById(eventId).orElse(null);
		Authentication loggedUser = SecurityContextHolder.getContext().getAuthentication();
		String userName = loggedUser.getName();
		User cuser = urepo.findByUsername(userName);

		if (newComment.getContent() == "") {
			return "redirect:" + referer;
		}
		System.out.println("ASDASDASDASDASDASDDDDDDDDDDDDDDDDD");
		
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		newComment.setDatetime(formatter.format(date));
		newComment.setOwner(cuser);
		newComment.setEvent(newEvent);

		System.out.println(newComment);
		
		crepo.save(newComment);
		return "redirect:" + referer;
	}
	// ADD USER
	@RequestMapping(value = "/signin")
	  public String addUser(Model model) {
	    model.addAttribute("user", new User());
	    return "signinpage";
	  }
	// SAVE USER
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	  public String saveUser(User user) {
		final String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
		user.setPasswordHash(encodedPassword);
		user.setRole("USER");
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
	@RequestMapping(value="/events/save")
	public @ResponseBody Event saveEventRest(Event event) {
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
		return newEvent;
	}

	 
}
