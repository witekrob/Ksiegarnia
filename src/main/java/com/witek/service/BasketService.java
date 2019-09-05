package com.witek.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.witek.dao.BasketDao;
import com.witek.dao.BasketItemDao;
import com.witek.model.Basket;
import com.witek.model.BasketItem;
import com.witek.model.Book;
import com.witek.model.Client;

@Service
@Transactional
public class BasketService {
	private Basket basket;
	private BookService bookService;
	private List<Basket> orderHistory;
	private BasketItemService basketItemService;
	private ClientService clientService;
	@Autowired
	public BasketService(BasketItemService basketItemService, Basket basket, BookService bookService,
			ClientService clientService) {
		this.basket = basket;
		this.bookService = bookService;
		orderHistory = new ArrayList<Basket>();
		this.basketItemService = basketItemService;
		this.clientService=clientService;
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

	public void removeOneFromBasket(BasketItem item) {
		int itemId = item.getBasketItem_id();
		basket.getBasketItems().remove(itemId);
	}

	public void basketProceed(Basket basket) {
		System.out.println("zaczynam realizowac zamowienie");
		List<BasketItem> allItemsInBasket = basket.getBasketItems();
		Book toEdit = new Book();

		if (allItemsInBasket != null) {
			System.out.println("3");

			for (BasketItem item : allItemsInBasket) {
				System.out.println("4");
				toEdit = bookService.getById(item.getBook().getId_number());
				toEdit.setQuantity(toEdit.getQuantity() - item.getQuantity());
				System.out.println("5   " + toEdit);
				bookService.saveBook(toEdit);

			}
		}
		basketItemService.saveBasketItem(basket);
		clientService.addNewClient(basket.getClient());
//		clientDao.save(basket.getClient());
	}

	public int overallPrice(Basket basket2) {
		List<BasketItem> itemy = basket2.getBasketItems();
		int price = 0;

		for (BasketItem item : itemy) {
			price = price + item.getPrice();
		}

		return price;
	}
}
