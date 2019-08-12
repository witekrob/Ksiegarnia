package com.witek.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.witek.model.Basket;
import com.witek.model.BasketItem;
import com.witek.model.Book;
import com.witek.service.BasketService;
import com.witek.service.BookService;

@Controller
public class BasketController {
private BookService bookService;
private BasketService basketService;
private Basket basket = new Basket();
@Autowired
public BasketController(BookService bookService, BasketService basketService) {
//	this.basket=basket;
	this.bookService=bookService;
	this.basketService=basketService;
}
	
@PostMapping("/addToBasket")
public String addToBasket(Long id_number, int howMany,Model model) {
	Book book = bookService.getById(id_number);
	BasketItem basketItem = new BasketItem(0, book,howMany);
	basketService.addToBasket(basket,basketItem);
	model.addAttribute("basket", basket);
	
	return "basketContent";
}
@GetMapping("/clearWholeBasket")
public String clearWholeBasket() {
	basketService.clearWholeBasket(basket);
	return "basketContent";
}
@GetMapping("basketContent") 
public String basketContent(Model model) {
	model.addAttribute("basket", basket);
	return "basketContent";
}
}
