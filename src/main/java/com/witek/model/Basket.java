package com.witek.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

//@Entity
@Component
public class Basket {

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int basket_id;
	//@OneToOne(targetEntity=BasketItem.class)
	private List<BasketItem> basketItems; 

	public Basket() {
	}
	public Basket(int basket_id, List<BasketItem> basketItems) {
		this.basket_id=basket_id;
		this.basketItems=basketItems;
	}
	@Override
	public String toString() {
		return "Basket [basket_id=" + basket_id + ", basketItems=" + basketItems + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basketItems == null) ? 0 : basketItems.hashCode());
		result = prime * result + basket_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Basket other = (Basket) obj;
		if (basketItems == null) {
			if (other.basketItems != null)
				return false;
		} else if (!basketItems.equals(other.basketItems))
			return false;
		if (basket_id != other.basket_id)
			return false;
		return true;
	}

	public int getBasket_id() {
		return basket_id;
	}

	public void setBasket_id(int basket_id) {
		this.basket_id = basket_id;
	}

	public List<BasketItem> getBasketItems() {
		return basketItems;
	}

	public void setBasketItems(List<BasketItem> basketItems) {
		this.basketItems = basketItems;
	}

	
}