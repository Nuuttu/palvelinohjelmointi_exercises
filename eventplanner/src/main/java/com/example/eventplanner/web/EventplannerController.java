package com.example.eventplanner.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.eventplanner.domain.EventRepository;

@Controller
public class EventplannerController {
	
	@Autowired
	private EventRepository erepo;

	@RequestMapping(value="/")
	public String Index(Model model) {
		model.addAttribute("events", erepo.findAll());
		return "index";
	}
	
	

}
