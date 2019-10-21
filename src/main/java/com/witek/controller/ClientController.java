package com.witek.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.witek.model.Client;
import com.witek.model.PersonalDetails;
import com.witek.service.ClientService;

@Controller

public class ClientController {
private ClientService clientService; 
@Autowired
public ClientController(ClientService clientService) {
	this.clientService=clientService;
}

	
@GetMapping("registration")
public String registration(Client client) {
	return "registration";
}
@PostMapping("/registration")
public String registration(@ModelAttribute("client")@Valid Client client, BindingResult binding,Model model,HttpServletRequest request) {
	if(binding.hasErrors()) {
		System.out.println("błędów a błędów");
		return "registration";	
	}
	else
	clientService.addNewClient(client);
	request.getSession().setAttribute("client", client);	
	model.addAttribute("client",client);
	return "index";
	
}
@GetMapping("clientInfo")
public String clientInfo(Model model,HttpServletRequest request) {
//	Client client = (Client)request.getSession().getAttribute("client");
	Client client = clientService.getClient();
	
	
	System.out.println(client);
	if (client!=null) {
	model.addAttribute("client",client);
	return "clientInfo";}
	else {
		return "login";
	}
}
@GetMapping("logout")
public String logout(HttpServletRequest request) {
	clientService.logout(request);	
	System.out.println("log out");
	return "index";
}
@GetMapping("login")
public String login(Model model) {
	Client client = clientService.getClient();
model.addAttribute("client",client);
	return "login";
}
@PostMapping("login")
public String login(Model model,String email, String password,HttpServletRequest request) {
	System.out.println(email + " " +  password);
	Client client = clientService.login(email, password);
	if (client!=null) {
		System.out.println("valid");		
		request.getSession().setAttribute("client", client);	
		return "index";
	}
	String loginResult = ("couldn't find client that match this details" + " " + email + " " + password);
	model.addAttribute("loginResult", loginResult);
	return "login";
}
@PostMapping("/editDetails")
public String editDetails(@ModelAttribute PersonalDetails details,Model model, HttpServletRequest request) {
	Client client = clientService.getClient();
	client.setPersonalDetails(details);
	request.getSession().setAttribute("client",client);
	System.out.println(client);
	clientService.addNewClient(client);
	
	model.addAttribute("client",client);
	
	return "clientInfo";
}
}
