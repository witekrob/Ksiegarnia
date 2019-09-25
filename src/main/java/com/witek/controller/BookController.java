package com.witek.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.witek.model.Author;
import com.witek.model.Book;
import com.witek.model.Client;
import com.witek.model.Role;
import com.witek.service.AuthorService;
import com.witek.service.BookService;

@Controller
public class BookController {

	private List<Author> allAuthors = new ArrayList<Author>();
	private BookService bookService;
	private AuthorService authorService;

	@Autowired
	public BookController(BookService bookService, AuthorService authorService) {
		this.bookService = bookService;
		this.authorService = authorService;
	}

	@GetMapping("/bookDetails")
	public String getAll(Model model) {
		allAuthors = authorService.getAll();
		model.addAttribute("allAuthors", allAuthors);

		return "showAll";
	}

	@GetMapping("/bookDetails/{id}")
	public String bookDetails(@PathVariable Long id, Model model, HttpServletRequest request) {
		Book bookDetail = bookService.getById(id);

		Client client = (Client) request.getSession().getAttribute("client");
		if (client!=null) {
			model.addAttribute("client",client);
			}
		else {
			client = new Client();
				client.setRole(Role.UNKNOWN);
				model.addAttribute("client",client);
				
		}
		model.addAttribute("bookDetail", bookDetail);
		return "bookDetails";
	}

	@PostMapping("/bookDetails/{id}")
	public String editBookDetails(@PathVariable Long id, Book newBookDetails, Model model, HttpServletRequest request) {
Client client = (Client)request.getSession().getAttribute("client");
		Book bookToEdit = bookService.getById(id);
		System.out.println("book to edit : " + bookToEdit);
		bookToEdit = bookService.editBook(bookToEdit, newBookDetails);
		bookService.saveBook(bookToEdit);
		model.addAttribute("bookDetail", bookToEdit);
		model.addAttribute("client",client);
		return "bookDetails";
	}

	@GetMapping("/addNew")
	public String addNew() {
		return "addNew";

	}

	@PostMapping("/addNew")
	public String form(@ModelAttribute Book book, Author author, Model model) {
		Author result = authorService.checkIfExist(author);
		System.out.println("SPRAWDZAM : " + book + " " + author);

		System.out.println("wynik to :  " + result + "a wpisywany to : " + author);
		if (result != null) {
			author = result;
		}

		book.setAuthor(author);
		author.getBooks().add(book);
		bookService.saveBook(book);
		model.addAttribute("book", book);
		model.addAttribute("author", author);
		return "success";
	}

}
