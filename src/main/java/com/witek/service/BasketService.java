package com.witek.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.witek.model.Basket;
import com.witek.model.BasketItem;
import com.witek.model.Book;

@Service
public class BasketService {
	private Basket basket;
	private BookService bookService;
	private List<Basket> orderHistory;

	@Autowired
	public BasketService(Basket basket, BookService bookService) {
		this.basket = basket;
		this.bookService = bookService;
		orderHistory = new ArrayList<Basket>();

	}

	public void addToBasket(Basket basket, BasketItem basketItem) {
		if (basket.getBasketItems() == null) {
			List<BasketItem> itemy = new ArrayList<BasketItem>();
			basket.setBasketItems(itemy);
		}
		basket.getBasketItems().add(basketItem);
		System.out.println("dodano do koszyka item: " + basketItem);
		System.out.println("Zawartość koszyka : " + basket);

	}

	public void clearWholeBasket(Basket basket) {
		if (basket.getBasketItems() != null)
			basket.getBasketItems().clear();
	}

	public int sumPrice(BasketItem basketItem) {
		int quantity = basketItem.getQuantity();
		int pricePerUnit = basketItem.getBook().getPrice();
		return quantity * pricePerUnit;
	}

	public void addToBasket(BasketItem item) {
		basket.getBasketItems().add(item);
	}

	public void removeOneFromBasket(BasketItem item) {
		int itemId = item.getBasketItem_id();
		basket.getBasketItems().remove(itemId);
	}

	public void basketProceed(Basket basket) {
		System.out.println("zaczynam");
		List<Book> allBooks = bookService.getAllBooks();
		List<BasketItem> allItems = basket.getBasketItems();
		List<Book> booksOrdered;
		if (allItems != null)
			for (BasketItem item : allItems) {

				for (Book b : allBooks) {
					if (b.getId_number() == item.getBook().getId_number()) {
						b.setQuantity(b.getQuantity() - item.getQuantity());
						bookService.saveBook(b);
					}

				}
			}
	}

	public List<Basket> addToHistory(Basket basket) {
		orderHistory.add(basket);
		System.out.println("historia zamówień : " + orderHistory);
		return orderHistory;
	}

	public int overallPrice(Basket basket2) {
		List<BasketItem> itemy = basket2.getBasketItems();
		int price=0;
		
		for (BasketItem  item:itemy) {
			price=price+item.getPrice();
		}
		
		
		return price;
	}
}
