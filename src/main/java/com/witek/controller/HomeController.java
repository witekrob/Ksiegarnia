package com.witek.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.witek.dao.AuthorDao;
import com.witek.dao.BookDao;
import com.witek.model.Author;
import com.witek.model.Book;
import com.witek.service.AuthorService;
import com.witek.service.BookService;

@Controller
public class HomeController {

	private AuthorService authorService;
	private BookService bookService;
	private List<Author> allAuthors = new ArrayList<Author>();

	@Autowired
	public HomeController(BookService bookService, AuthorService authorService) {
		this.authorService = authorService;
		this.bookService = bookService;
	}

	@GetMapping("/")
	public String goHome(Model model) {
		if (!allAuthors.isEmpty()) {
			allAuthors.clear();
			model.addAttribute("allAuthors", allAuthors);
			return "index";

		} else {
			allAuthors = authorService.getAll();
			model.addAttribute("allAuthors", allAuthors);
			return "index";
		}
	}

	@PostMapping("/add")
	public String form(@ModelAttribute Book book, Author author, Model model) {
		book.setAuthor(author);
		author.getBooks().add(book);
		bookService.saveBook(book);

		System.out.println("author " + author);
		System.out.println(author.getBooks());
		model.addAttribute("book", book);
		model.addAttribute("author", author);
		return "success";
	}

}
