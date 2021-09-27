package com.example.bookstore.web;

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

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.BookRepository;
import com.example.bookstore.domain.CategoryRepository;

@Controller
public class BookController {

  @Autowired
  private CategoryRepository catrepo;

  @Autowired
  private BookRepository repository;

  @RequestMapping(value="/login")
  public String login() {	
      return "login";
  }	

  @RequestMapping(value = { "/", "/bookstore" })
  public String books(Model model) {
    model.addAttribute("books", repository.findAll());
    return "bookstore";
  }

  @RequestMapping(value = "/books", method = RequestMethod.GET)
  public @ResponseBody List<Book> bookListRest() {
    return (List<Book>) repository.findAll();
  }

  @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
  public @ResponseBody Optional<Book> FindBookRest(@PathVariable("id") Long bookId) {
    return repository.findById(bookId);
  }

  @RequestMapping(value = "/add")
  public String addBook(Model model) {
    model.addAttribute("book", new Book());
    model.addAttribute("categories", catrepo.findAll());
    return "addbook";
  }

  @RequestMapping(value = { "/save" }, method = RequestMethod.POST)
  public String save(Book book) {
    repository.save(book);
    return "redirect:bookstore";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public String editBook(@PathVariable("id") Long bookId, Model model) {
    model.addAttribute("book", repository.findById(bookId));
    model.addAttribute("categories", catrepo.findAll());
    return "editbook";
  }

  @RequestMapping(value = "/edit/save", method = RequestMethod.POST)
  public String edit(Book book) {
    /*
     * if (book.getId() != null ) {
     * 
     * Book editedBook = repository.findById(book.getId()).orElse(null);
     * 
     * editedBook.setId(book.getId()); editedBook.setTitle(book.getTitle());
     * editedBook.setAuthor(book.getAuthor()); editedBook.setYear(book.getYear());
     * editedBook.setIsbn(book.getIsbn()); editedBook.setPrice(book.getPrice());
     * repository.save(editedBook);
     * 
     * }
     */
    repository.save(book);
    return "redirect:../bookstore";
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  public String deleteBook(@PathVariable("id") Long bookId, Model model) {
    repository.deleteById(bookId);
    return "redirect:../bookstore";
  }

}
