package com.witek.controller;

import java.util.ArrayList;
import java.util.Iterator;
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
private int overallPrice;
private int price;
private List<Basket> orderHistory;
int basketItem_id = 0;
@Autowired
public BasketController(BookService bookService, BasketService basketService) {

	this.bookService=bookService;
	this.basketService=basketService;
}
	
@PostMapping("/addToBasket")
public String addToBasket(Long id_number, int howMany,Model model) { 
	Book book = bookService.getById(id_number);
	if (basket.getBasketItems()!=null) {
	int basketSize = basket.getBasketItems().size();
	basketItem_id=basketSize;
	}
	howMany=Math.abs(howMany);
	price = book.getPrice() * howMany;
	BasketItem basketItem = new BasketItem(basketItem_id,book,howMany,price);
	basketService.addToBasket(basket,basketItem);
	model.addAttribute("basket", basket);
	overallPrice= basketService.overallPrice(basket);
	
	
	//overallPrice = overallPrice + price;
	model.addAttribute("overallPrice",overallPrice);
	
	return "basketContent";
}
@GetMapping("/clearWholeBasket")
public String clearWholeBasket() {
	System.out.println("Zaczynam czyscic koszyk");
	//basket = basketService.removeEverything();
	System.out.println(basket.getBasketItems());
	System.out.println("wyczyszczony koszyk");
	basket = new Basket();
	overallPrice = 0;
	return "basketContent";
}
@GetMapping("basketContent") 
public String basketContent(Model model) {
	model.addAttribute("basket", basket);
	model.addAttribute("price",price);
	model.addAttribute("overallPrice",overallPrice);
	
	return "basketContent";
}
@PostMapping("basketProceed")
public String basketProceed(Model model) {
	basketService.basketProceed(basket);
	orderHistory = basketService.addToHistory(basket);
	
	basket = new Basket();	
	overallPrice=0;
	model.addAttribute("orderHistory",orderHistory);
	return "orderHistory";
}
@GetMapping("orderHistory")
public String orderHistory(Model model) {
	model.addAttribute("orderHistory",orderHistory);
	return "orderHistory";
}
@PostMapping("changeItemQuantity")
public String remove1fromBasket(int newQuantity, int basketItem_id,Model model) {
	 System.out.println("basketItem_id: " +basketItem_id + "new quantity : " +  newQuantity);
	BasketItem itemToChange = basket.getBasketItems().get(basketItem_id);
	itemToChange.setQuantity(newQuantity);
	int newPrice = itemToChange.getQuantity()*itemToChange.getBook().getPrice();
	
	itemToChange.setPrice(newPrice);
	overallPrice=basketService.overallPrice(basket);
	model.addAttribute("basket", basket);
	model.addAttribute("overallPrice",overallPrice);
	return "basketContent";
}
}
