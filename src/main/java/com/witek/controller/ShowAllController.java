package com.witek.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.witek.model.Author;
import com.witek.service.AuthorService;
import com.witek.service.BookService;
@Controller
public class ShowAllController {

	
	private AuthorService authorService;
	private BookService bookService;
	private List<Author> allAuthors = new ArrayList<Author>();
	
	@Autowired
	public ShowAllController(AuthorService authorService, BookService bookService) {
		this.authorService=authorService;
		this.bookService=bookService;
	}
	@GetMapping("/showAll")
	public String showAll(Model model) {
	allAuthors = authorService.getAll();
	model.addAttribute("allAuthors",allAuthors);
		return "showAll";
	}
}
