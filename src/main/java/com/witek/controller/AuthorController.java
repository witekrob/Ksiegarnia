package com.witek.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.witek.model.Author;
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
	public void getAuthorDetails(@PathVariable Long author_id) {
		System.out.println("NUMER AUTORA :  " + author_id);
		Author autor = authorService.getById(author_id);
		System.out.println(autor);
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
