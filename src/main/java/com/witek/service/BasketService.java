package com.witek.service;

import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.witek.dao.BasketDao;
import com.witek.model.Basket;
import com.witek.model.BasketItem;
import com.witek.model.Book;
import com.witek.model.Client;

@Service
@Transactional
public class BasketService {
	private Basket basket;
	private BookService bookService;
	private ClientService clientService;
	private BasketDao basketDao;

	@Autowired
	public BasketService(BasketDao basketDao, Basket basket, BookService bookService, ClientService clientService) {
		this.basket = basket;
		this.bookService = bookService;
		this.clientService = clientService;
		this.basketDao = basketDao;
	}

	public BasketService() {
	}

	public Basket clearWholeBasket(Basket basket) {
		basket = new Basket();
		List<BasketItem> itemy = new ArrayList<BasketItem>();
		itemy = null;
		basket.setBasketItems(itemy);
		return basket;
	}

	public int sumPrice(BasketItem basketItem) {
		int quantity = basketItem.getQuantity();
		int pricePerUnit = basketItem.getBook().getPrice();
		return quantity * pricePerUnit;
	}

	public void removeOneFromBasket(BasketItem item) {
		basket.getBasketItems().remove(item.getBasketItem_id());
	}

	public boolean basketProceed(HttpServletRequest request) {

		Basket basket = (Basket) request.getSession().getAttribute("basket");
		if (basket == null) {
			return false;
		} else
			System.out.println("zaczynam realizowac zamowienie");
		List<BasketItem> allItemsInBasket = basket.getBasketItems();
		Book toEdit = new Book();
		Client client = (Client) request.getSession().getAttribute("client");
		basket.setClient(client);

		// Client client = basket.getClient();
		List<Basket> history = clientService.getOrderHistory(client);
		if (allItemsInBasket == null) {
			return false;
		} else {
			System.out.println("3");

			for (BasketItem item : allItemsInBasket) {
				System.out.println("4");
				toEdit = bookService.getById(item.getBook().getId_number());
				toEdit.setQuantity(toEdit.getQuantity() - item.getQuantity());
				System.out.println("5   " + toEdit);
				System.out.println("zawartosc koszyka : " + allItemsInBasket);

			}
			bookService.saveBook(toEdit);
			history.add(basket);
			client.setBasketHistory(history);

			clientService.addNewClient(client);
			// saveBasket(basket);
			return true;

		}
		// return false;
	}

	public int overallPrice(Basket basket) {
		List<BasketItem> itemy = basket.getBasketItems();
		int price = 0;

		for (BasketItem item : itemy) {
			price = price + item.getPrice();
		}
		return price;
	}

	public void saveBasket(Basket basket) {
		basketDao.save(basket);
	}

	public Basket addToBasket(HttpServletRequest request, List<BasketItem> itemsInBasket, Long id_number, int howMany,
			Client client, int overallPrice) {
		int price;
		String message;
		Basket basket = (Basket) request.getSession().getAttribute("basket");

		if (basket == null) {
			basket = new Basket();
		}

		BasketItem basketItem = new BasketItem();

		basket.setData(new Date());
		basket.setClient(client);
		Book book = bookService.getById(id_number);
		int howManyLeft = book.getQuantity();
		if (howManyLeft == 0 || howManyLeft < howMany) {
			System.out.println("Too low stock");
			return basket;
		}

		price = book.getPrice() * howMany;
		basketItem.setBook(book);
		basketItem.setQuantity(howMany);
		basketItem.setPrice(price);
		basketItem.setBasket(basket);
		itemsInBasket.add(basketItem);
		basket.setBasketItems(itemsInBasket);
		return basket;
	}
}
