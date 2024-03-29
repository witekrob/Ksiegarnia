package com.witek.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.witek.model.Author;
import com.witek.service.AuthorService;
import com.witek.service.BookService;

import com.witek.model.Client;

@Controller
public class HomeController {


	@Autowired
	public HomeController() {
	}
	@GetMapping("/")
	public String goHome(HttpServletRequest request, Client client, Model model) {
		client = (Client)request.getSession().getAttribute("client");
		System.out.println(client);
		String message=null;
		if (client!=null) {
			message="Jestes zalogowany";
		model.addAttribute("client",client);
		}
		else {
			message="niezalogowany";
		}
		model.addAttribute("client",client); 
		model.addAttribute("message",message);
		
		return "index";
}
}
 