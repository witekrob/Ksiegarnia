package com.witek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.witek.model.Author;
import com.witek.model.Client;
import com.witek.model.Role;
import com.witek.service.AuthorService;

@Controller
public class AuthorController {

	private AuthorService authorService;

	@Autowired
	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping("/findBy")
	public String findBy() {
		return "find";
	}

	@GetMapping("/authorDetails/{author_id}")
	public String getAuthorDetails(@PathVariable Long author_id, Model model, HttpServletRequest request) {
		System.out.println("NUMER AUTORA :  " + author_id);
		Author author = authorService.getById(author_id);
		System.out.println(author);
		Client client = (Client)request.getSession().getAttribute("client");
		
		if (client!=null) {
			model.addAttribute("client",client);
			}
		else {
			client = new Client();
				client.setRole(Role.UNKNOWN);
				model.addAttribute("client",client);
				
		}
		model.addAttribute("author", author);
		return "authorDetails";
	}

	@PostMapping("/authorDetails/{author_id}")
	public String editAuthorDetails(@PathVariable Long author_id, Author newDetailsAuthor, Model model) {
		Author authorToEdit = authorService.getById(author_id);
		System.out.println(" NOWE DANE  :   " + newDetailsAuthor);
		System.out.println(" Autor do przerobienia  :   " + authorToEdit);
		authorToEdit = authorService.editAuthor(newDetailsAuthor, authorToEdit);
		System.out.println(" Autor po przerobieniu  :   " + authorToEdit);
		model.addAttribute("author", authorToEdit);
		return "authorDetails";

	}

	@PostMapping("/findBy")
	public String findBy(@RequestParam String toFind, String findBy, Model model) {
		List<Author> find = new ArrayList<Author>();
		System.out.println("czego szukasz : " + toFind + " __ według : " + findBy);

		switch (findBy) {
		case "surname":
			find = authorService.findBySurname(toFind);
			break;
		case "country":
			find = authorService.findByCountry(toFind);
			break;
		}
		model.addAttribute("find", find);
		return "findingResult";
	}

}
