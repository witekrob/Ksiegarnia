package com.witek.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.witek.dao.BasketItemDao;
import com.witek.model.Basket;
import com.witek.model.BasketItem;
import com.witek.model.Book;
import com.witek.service.BasketItemService;
import com.witek.service.BasketService;
import com.witek.service.BookService;
import com.witek.service.ClientService;

import com.witek.model.Client;

@Controller
public class BasketController {
	private BookService bookService;
	private BasketService basketService;
	private Basket basket = new Basket();
	List<BasketItem> itemsInBasket;
	private Date date;
	private int overallPrice = 0;
	private int price;
	private ClientService clientService;
	private Client client;
	private BasketItemService itemService;

	@Autowired
	public BasketController(BookService bookService, BasketService basketService, ClientService clientService,BasketItemService itemService) {
		this.bookService = bookService;
		this.basketService = basketService;
		this.clientService = clientService;
		this.itemService = itemService;
		
	}

	@PostMapping("/addToBasket")
	public String addToBasket(HttpServletRequest request, Long id_number, int howMany, Model model) {
		client =clientService.getClient();
		basket = (Basket) request.getSession().getAttribute("basket");
		if (basket == null) {
			basket = new Basket();
		}
		itemsInBasket = basket.getBasketItems();
		if (itemsInBasket == null) {
			itemsInBasket = new ArrayList<BasketItem>();
		}

		basket = basketService.addToBasket(request, itemsInBasket, id_number, howMany, client, overallPrice);
		overallPrice = basketService.overallPrice(basket);
		basket.setOverallBasketPrice(overallPrice);

		request.getSession().setAttribute("basket", basket);
		model.addAttribute("basket", basket);
		model.addAttribute("client", client);
		request.getSession().setAttribute("overallPrice", overallPrice);
		model.addAttribute("overallPrice", overallPrice);

		return "basketContent";
	}

	@GetMapping("/clearWholeBasket")
	public String clearWholeBasket(HttpServletRequest request, Model model) {
		//Client client = (Client) request.getSession().getAttribute("client");
		client =clientService.getClient();
		
		System.out.println("Zaczynam czyscic koszyk");
		basket = basketService.clearWholeBasket(basket);
		overallPrice = 0;
		itemsInBasket = new ArrayList<BasketItem>();
		String message = "Koszyk wyczyszczony";

		request.getSession().setAttribute("basket", basket);
		request.getSession().setAttribute("overallPrice", overallPrice);
		model.addAttribute("client", client);
		model.addAttribute("message", message);
		return "index";
	}

	@GetMapping("basketContent")
	public String basketContent(Model model, HttpServletRequest request) {
		//Client client = (Client) request.getSession().getAttribute("client");
		client =clientService.getClient();
		
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		model.addAttribute("client", client);
		model.addAttribute("basket", basket);
		model.addAttribute("overallPrice", overallPrice);

		return "basketContent";
	}

	@PostMapping("basketProceed")
	public String basketProceed(Model model, HttpServletRequest request) {
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		if (basket == null) {
			basket = new Basket();
		}
		//Client client = (Client) request.getSession().getAttribute("client");
		String message = null;

		boolean proceed = basketService.basketProceed(request);

		if (proceed == true) {
			System.out.println("history made");
			basketService.clearWholeBasket(basket);
			basket = new Basket();
			overallPrice = 0;
			itemsInBasket = new ArrayList<BasketItem>();
			request.getSession().setAttribute("overallPrice", overallPrice);
			request.getSession().setAttribute("basket", basket);
			message = "Wszystko poszło dobrze";
		} else {
			message = "Nie dodano nic do koszyka";
		}

		model.addAttribute("message", message);
		return "index";
	}

	@GetMapping("orderHistory")
	public String orderHistory(Model model, HttpServletRequest request) {
		//Client client = (Client) request.getSession().getAttribute("client");
		client =clientService.getClient();
		
		List<Basket> orderHistory = new ArrayList<Basket>();
		if (client != null) {
			orderHistory = clientService.getOrderHistory(client);
		}
		model.addAttribute("client", client);
		model.addAttribute("orderHistory", orderHistory);
		return "orderHistory";
	}

	@PostMapping("changeItemQuantity")
	public String changeItemQuantity(int newQuantity, String bookTitleToChange, Model model, HttpServletRequest request) {
		String title = bookTitleToChange;
		Basket basket = (Basket) request.getSession().getAttribute("basket");
		
		BasketItem itemToChange = basket.getBasketItems().stream().filter(x->x.getBook().getTitle().equals(title)).findAny().get();

		System.out.println(itemToChange);

		
		itemToChange.setQuantity(newQuantity);
		int newPrice = itemToChange.getQuantity() * itemToChange.getBook().getPrice();
		itemToChange.setPrice(newPrice);
		overallPrice = basketService.overallPrice(basket);
		basket.setOverallBasketPrice(overallPrice);
		model.addAttribute("basket", basket);
		System.out.println(basket);

		request.getSession().setAttribute("basket", basket);
		return "basketContent";
	}
}