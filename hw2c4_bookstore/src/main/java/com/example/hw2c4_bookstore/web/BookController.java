package com.example.hw2c4_bookstore.web;

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

import com.example.hw2c4_bookstore.domain.Book;


@Controller
public class BookController {
	
	private List<Book> books = new ArrayList<Book>();
	
	@GetMapping("/index")
	public String friendForm(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("books", books);
		return "index";
	}
	
	@PostMapping("/index")
	public String friendSubmit(@ModelAttribute Book book, Model model) {
		model.addAttribute("book", book);
		books.add(book);
		return "redirect:/index";
	}
	
	
}
