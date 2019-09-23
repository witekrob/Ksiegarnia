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
	private Basket basket;// = new Basket();
	List<BasketItem> itemsInBasket;
	
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
		if (basket==null) {
			itemsInBasket = new ArrayList<BasketItem>();
			
			basket= new Basket();
			basket.setClient(client);
			overallPrice=0;
		}
		Book book = bookService.getById(id_number);
		howMany = Math.abs(howMany);
		price = book.getPrice() * howMany;
		
		BasketItem basketItem = new BasketItem();
		basketItem.setBook(book);
		basketItem.setQuantity(howMany);
		basketItem.setPrice(price);
		int basketItemId = 0;
		basketItem.setBasketItem_id(basketItemId++);
		itemsInBasket.add(basketItem);
		basket.setBasketItems(itemsInBasket);
		request.getSession().setAttribute("basket", basket);;
	
		model.addAttribute("basket", basket);
		model.addAttribute("client", client);
		overallPrice = basketService.overallPrice(basket);
		request.getSession().setAttribute("overallPrice", overallPrice);
		
		model.addAttribute("overallPrice", overallPrice);

		return "basketContent";
	}

	@GetMapping("/clearWholeBasket")
	public String clearWholeBasket(HttpServletRequest request,Model model) {
		Client client = (Client) request.getSession().getAttribute("client");
	//	Basket basket=(Basket)request.getSession().getAttribute("basket");
		model.addAttribute("client",client);
		System.out.println("Zaczynam czyscic koszyk");
		basket = basketService.clearWholeBasket(basket);
		overallPrice = 0;
		itemsInBasket = new ArrayList<BasketItem>();
		
		request.getSession().setAttribute("basket",basket);
		request.getSession().setAttribute("overallPrice",overallPrice);
		
		String message = "Koszyk wyczyszczony";
		model.addAttribute("message",message);
		return "index";
	}
	@GetMapping("basketContent")
	public String basketContent(Model model, HttpServletRequest request) {
		Client client = (Client) request.getSession().getAttribute("client");
		
		Basket basket=(Basket)request.getSession().getAttribute("basket");
		model.addAttribute("client", client);
		model.addAttribute("basket", basket);
		System.out.println(basket);
		model.addAttribute("overallPrice", overallPrice);

		return "basketContent";
	}

	@PostMapping("basketProceed")
	public String basketProceed(Model model, HttpServletRequest request) {
		Basket basket = (Basket)request.getSession().getAttribute("basket");
		Client client = (Client)request.getSession().getAttribute("client");
		String message=null;
		orderHistory= clientService.getOrderHistory(client);
			
		basket.setClient(client);
		boolean proceed = basketService.basketProceed(request);
		
		if (proceed==true) {
		//orderHistory.add(basket);
		//client.setBasketHistory(orderHistory);
		System.out.println(orderHistory);
		//request.getSession().setAttribute("orderHistory", orderHistory);
		System.out.println("history made");
		basket = new Basket();
		overallPrice = 0;
		itemsInBasket = new ArrayList<BasketItem>();
		request.getSession().setAttribute("overallPrice",overallPrice);
		request.getSession().setAttribute("basket",basket);
		message = "Wszystko poszło dobrze";
		}
		else {
			message= "Nie dodano nic do koszyka";
		}
		
		model.addAttribute("message",message);
		return "index";
	}

	@GetMapping("orderHistory")
	public String orderHistory(Model model, HttpServletRequest request) {
	Client client = (Client)request.getSession().getAttribute("client");
	
	if (client!=null) {
		orderHistory = clientService.getOrderHistory(client);
	}
		request.getSession().setAttribute("orderHistory", orderHistory);
		model.addAttribute("client",client);
		model.addAttribute("orderHistory",orderHistory);
		return "orderHistory";
	}

	@PostMapping("changeItemQuantity")
	public String remove1fromBasket(int newQuantity, int basketItem_id, Model model, HttpServletRequest request) {
		System.out.println("basketItem_id: " + basketItem_id + "new quantity : " + newQuantity);
		Basket basket=(Basket)request.getSession().getAttribute("basket");
		Client client = (Client)request.getSession().getAttribute("client");
		BasketItem itemToChange = basket.getBasketItems().get(basketItem_id);
		itemToChange.setQuantity(newQuantity);
		int newPrice = itemToChange.getQuantity() * itemToChange.getBook().getPrice();
		itemToChange.setPrice(newPrice);
		overallPrice = basketService.overallPrice(basket);
		model.addAttribute("basket", basket);
		model.addAttribute("client",client);
		model.addAttribute("overallPrice", overallPrice);
		
		return "basketContent";
	}
}
