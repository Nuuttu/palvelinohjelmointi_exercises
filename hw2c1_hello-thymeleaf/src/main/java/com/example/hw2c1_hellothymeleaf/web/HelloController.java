package com.example.hw2c1_hellothymeleaf.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hw2c1_hellothymeleaf.domain.Person;

@Controller
public class HelloController {
	@RequestMapping("/hello")
	public String greeting(@RequestParam(name = "name", defaultValue = "dd") String name,
			@RequestParam(name = "age", defaultValue = "1") Integer age, Model model) {

		Person person1 = new Person();
		person1.setName(name);
		person1.setAge(age);

		model.addAttribute("person1", person1);
		return "hello";
	}
}
