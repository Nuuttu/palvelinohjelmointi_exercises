package com.example.bookstore.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;

@Controller
public class BookController {
	
	
	
	@Autowired
	private BookRepository repository; 
	
    @RequestMapping(value= {"/", "/bookstore"})
    public String books(Model model) {
        model.addAttribute("books", repository.findAll());
        return "bookstore";
    }
  
    @RequestMapping(value = "/add")
    public String addBook(Model model){
    	model.addAttribute("book", new Book());
        return "addbook";
    }
    
    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    public String save(Book book){
	    repository.save(book);
        return "redirect:bookstore";
    }
  
	
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBook(@PathVariable("id") Long bookId, Model model){
    	model.addAttribute("book", repository.findById(bookId));
        return "editbook";
    }
    
    @RequestMapping(value = "/edit/save", method = RequestMethod.POST)
    public String edit(Book book){
    	/*
	    if (book.getId() != null ) {
	    	
	    	Book editedBook = repository.findById(book.getId()).orElse(null);
	    	
	    	editedBook.setId(book.getId());
	    	editedBook.setTitle(book.getTitle());
	    	editedBook.setAuthor(book.getAuthor());
	    	editedBook.setYear(book.getYear());
	    	editedBook.setIsbn(book.getIsbn());
	    	editedBook.setPrice(book.getPrice());
			repository.save(editedBook);
			
	    }
	    */
	    repository.save(book);
        return "redirect:../bookstore";
    }
       

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteBook(@PathVariable("id") Long bookId, Model model) {
    	repository.deleteById(bookId);
        return "redirect:../bookstore";
    }

    
}
