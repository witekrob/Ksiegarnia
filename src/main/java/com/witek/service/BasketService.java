package com.witek.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.witek.model.Basket;
import com.witek.model.BasketItem;

@Service
public class BasketService {

	
	
	
public void addToBasket(Basket basket,BasketItem basketItem) {
	if (basket.getBasketItems()==null) {
		List<BasketItem> itemy = new ArrayList<BasketItem>();
		basket.setBasketItems(itemy);	
		}
		basket.getBasketItems().add(basketItem);
		System.out.println("dodano do koszyka item: " + basketItem);
		System.out.println("Zawartość koszyka : " + basket);
		
}
public void clearWholeBasket(Basket basket) {
	basket.getBasketItems().clear();
	
}
}

