package com.example.hw2c3_friendlist.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.example.hw2c3_friendlist.domain.Friend;


@Controller
public class HelloController {
	
	private List<Friend> friends = new ArrayList<Friend>();
	@GetMapping("/index")
	public String friendForm(@RequestParam(name="name", required=false) String name, Model model) {
		model.addAttribute("friend", new Friend());
		model.addAttribute("friends", friends);
		return "index";
	}
	
	@PostMapping("/index")
	public String friendSubmit(@ModelAttribute Friend friend, Model model) {
		model.addAttribute("friend", friend);
		friends.add(friend);
		return "redirect:/index?name=" + friend.getName();
	}
	
	
}
