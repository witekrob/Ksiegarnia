package com.witek.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.witek.dao.BasketDao;
import com.witek.model.Basket;
import com.witek.model.BasketItem;
import com.witek.model.Book;

@Service
@Transactional
public class BasketService {
	private Basket basket;
	private BookService bookService;
	ClientService clientService;
	private BasketDao basketDao;

	@Autowired
	public BasketService(BasketDao basketDao, Basket basket,
			BookService bookService, ClientService clientService) {
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
		int itemId = item.getBasketItem_id();
		basket.getBasketItems().remove(itemId);
	}

	public boolean basketProceed(HttpServletRequest request) {

		Basket basket = (Basket) request.getSession().getAttribute("basket");
		System.out.println("zaczynam realizowac zamowienie");
		List<BasketItem> allItemsInBasket = basket.getBasketItems();
		Book toEdit = new Book();

		if (allItemsInBasket==null) {
			return false;
		}
		else {
			System.out.println("3");

			for (BasketItem item : allItemsInBasket) {
				System.out.println("4");
				toEdit = bookService.getById(item.getBook().getId_number());
				toEdit.setQuantity(toEdit.getQuantity() - item.getQuantity());
				System.out.println("5   " + toEdit);
				bookService.saveBook(toEdit);
				clientService.addNewClient(basket.getClient());
				saveBasket(basket);
				return true;
			}
		}
		return false;
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
}
