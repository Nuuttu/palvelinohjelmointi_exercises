package com.example.hw2c2_studentlist.web;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.hw2c2_studentlist.domain.Student;


@Controller
public class HelloController {

	
	@RequestMapping("/hello")
	public String Students(Model model) {
		
		Student Student1 = new Student();
		Student1.setFirstName("Moi");
		Student1.setLastName("Moi");
		Student Student2 = new Student();
		Student2.setFirstName("Hei");
		Student2.setLastName("Hei");
		Student Student3 = new Student();
		Student3.setFirstName("Hei");
		Student3.setLastName("Moiu");
		
		List<Student> Students = new ArrayList<Student>();
		Students.add(Student1);
		Students.add(Student2);
		Students.add(Student3);
		
		model.addAttribute("students", Students);
		return "hello";
	}
}
