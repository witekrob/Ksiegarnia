package com.witek.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
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
import com.witek.service.ClientService;
import com.witek.model.Client;

@Controller
public class BasketController {
	private BookService bookService;
	private BasketService basketService;
	private Basket basket ;//= new Basket();
	private int overallPrice= 0;
	private int price;
	private List<Basket> orderHistory;
	private Client client;
	private ClientService clientService;
	int basketItem_id = 0;

	@Autowired
	public BasketController(BookService bookService, BasketService basketService, ClientService clientService) {
		this.bookService = bookService;
		this.basketService = basketService;
		this.clientService = clientService;
	}

	@PostMapping("/addToBasket")
	public String addToBasket(HttpServletRequest request, Long id_number, int howMany, Model model) {
client = 	(Client)request.getSession().getAttribute("client");
//		client = clientService.getClient();
		basket = (Basket)request.getSession().getAttribute("basket");
		if (basket==null) {
			basket= new Basket();
			basket.setClient(client);
		}
		basket.setClient(client);
		Book book = bookService.getById(id_number);

		//if (basket.getBasketItems() != null) {
			//int basketSize = basket.getBasketItems().size();
			//basketItem_id = basketSize;
		//}
		howMany = Math.abs(howMany);
		price = book.getPrice() * howMany;
		BasketItem basketItem = new BasketItem(basketItem_id, book, howMany, price);
		basketService.addToBasket(basket, basketItem);
		request.getSession().setAttribute("basket", basket);;
	
		model.addAttribute("basket", basket);
		model.addAttribute("client", client);
		overallPrice = basketService.overallPrice(basket);

		model.addAttribute("overallPrice", overallPrice);

		return "basketContent";
	}

	@GetMapping("/clearWholeBasket")
	public String clearWholeBasket(HttpServletRequest request) {
		System.out.println("Zaczynam czyscic koszyk");
		// basket = basketService.removeEverything();
		System.out.println(basket.getBasketItems());
		System.out.println("wyczyszczony koszyk");			
		basket = new Basket();
		request.getSession().setAttribute("basket",basket);

		overallPrice = 0;
		return "basketContent";
	}
	@GetMapping("basketContent")
	public String basketContent(Model model, HttpServletRequest request) {
		Client client = (Client) request.getSession().getAttribute("client");
		overallPrice = (int)request.getSession().getAttribute("overallPrice");	
		Basket basket=(Basket)request.getSession().getAttribute("basket");
		
		if (client != null) {
			model.addAttribute("client", client);
			//basket.setClient(client);
		}
		
		model.addAttribute("basket", basket);
		model.addAttribute("price", price);
		model.addAttribute("overallPrice", overallPrice);

		return "basketContent";
	}

	@PostMapping("basketProceed")
	public String basketProceed(Model model, HttpServletRequest request) {
		Basket basket = (Basket)request.getSession().getAttribute("basket");
		Client client = (Client)request.getSession().getAttribute("client");
if (client==null ) {
	System.out.println("client is NUKLLLL");
}
		basket.setClient(client);
		basketService.basketProceed(basket);
		orderHistory = basketService.addToHistory(basket);;
		System.out.println("history made");
		
		basket = new Basket();
		request.getSession().setAttribute("overallPrice",0);
		request.getSession().setAttribute("basket",basket);
			
		return "index";
	}

	@GetMapping("orderHistory")
	public String orderHistory(Model model, HttpServletRequest request) {
		Client client = (Client)request.getSession().getAttribute("client");
		List<Basket> orderHistory = new ArrayList<Basket>();
		orderHistory = client.getBasketHistory();
		model.addAttribute("orderHistory",orderHistory);
		//model.addAttribute("client", client);
		return "orderHistory";
	}

	@PostMapping("changeItemQuantity")
	public String remove1fromBasket(int newQuantity, int basketItem_id, Model model) {
		System.out.println("basketItem_id: " + basketItem_id + "new quantity : " + newQuantity);
		BasketItem itemToChange = basket.getBasketItems().get(basketItem_id);
		itemToChange.setQuantity(newQuantity);
		int newPrice = itemToChange.getQuantity() * itemToChange.getBook().getPrice();
		itemToChange.setPrice(newPrice);
		overallPrice = basketService.overallPrice(basket);
		model.addAttribute("basket", basket);
		model.addAttribute("overallPrice", overallPrice);
		
		return "basketContent";
	}
}
