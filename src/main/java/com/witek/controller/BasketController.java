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

import com.witek.dao.ClientDao;
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
	private Basket basket = new Basket();
	private int overallPrice= 0;
	private int price;
	private List<Basket> orderHistory;
	private Client client;
	private ClientService clientService;

	@Autowired
	public BasketController(BookService bookService, BasketService basketService, ClientService clientService) {
		this.bookService = bookService;
		this.basketService = basketService;
		this.clientService = clientService;
	}

	@PostMapping("/addToBasket")
	public String addToBasket(HttpServletRequest request, Long id_number, int howMany, Model model) {
client = 	(Client)request.getSession().getAttribute("client");
		basket = (Basket)request.getSession().getAttribute("basket");
		List<BasketItem> itemy = new ArrayList<BasketItem>();
		if (basket==null) {
			basket= new Basket();
//		basket= new Basket();
			//basket.setClient(client);
		//}
		}
		basket.setClient(client);
		Book book = bookService.getById(id_number);
		howMany = Math.abs(howMany);
		price = book.getPrice() * howMany;
		
//		BasketItem basketItem = new BasketItem(0,book, howMany, price);
		BasketItem basketItem = new BasketItem();
		basketItem.setBook(book);
		basketItem.setQuantity(howMany);
		basketItem.setPrice(price);
		itemy.add(basketItem);
		basket.setBasketItems(itemy);
		//basket.getBasketItems().add(basketItem);
	
//		basketService.addToBasket(basket, basketItem);
		request.getSession().setAttribute("basket", basket);;
	
		model.addAttribute("basket", basket);
		model.addAttribute("client", client);
		overallPrice = basketService.overallPrice(basket);
		request.getSession().setAttribute("overallPrice", overallPrice);
		
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
		orderHistory= clientService.getOrderHistory(client);
		basket.setClient(client);
		orderHistory.add(basket);
		basketService.basketProceed(basket);
		request.getSession().setAttribute("orderHistory", orderHistory);
		System.out.println("history made");
		
		basket = new Basket();
		request.getSession().setAttribute("overallPrice",0);
		request.getSession().setAttribute("basket",basket);
			
		return "index";
	}

	@GetMapping("orderHistory")
	public String orderHistory(Model model, HttpServletRequest request) {
	Client client = (Client)request.getSession().getAttribute("client");
	//orderHistory = (List<Basket>)request.getSession().getAttribute("orderHistory");
		
	
	//List<Basket> orderHistory = new ArrayList<Basket>();
		//if (client!=null) {
		orderHistory = clientService.getOrderHistory(client);

		request.getSession().setAttribute("orderHistory", orderHistory);
		model.addAttribute("orderHistory",orderHistory);
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
