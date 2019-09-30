package com.witek.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Basket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int basket_id;
	@OneToMany(targetEntity=BasketItem.class, cascade=CascadeType.ALL)
	private List<BasketItem> basketItems;

	@ManyToOne(cascade=CascadeType.REFRESH)
	private Client client;
	private Date date;
	private int overallBasketPrice;
	public Basket() {
	}

	//public Basket(Client client,int basket_id, List<BasketItem> basketItems, Date date) {
	public Basket(int basket_id, List<BasketItem> basketItems, Date date, int overallBasketPrice) {
			
	this.basket_id = basket_id;
		this.basketItems = basketItems;
	//	this.client=client;
		this.overallBasketPrice=overallBasketPrice;
		this.date=date;
	}

	@Override
	public String toString() {
		return "Basket [basket_id=" + basket_id + ", basketItems=" + basketItems + ", date="
				+ date + "]";
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

//	public Client getClient() {
	//	return client;
	//}

	//public void setClient(Client client) {
	//	this.client = client;
	//}

	public Date getDate() {
		return date;
	}

	public void setData(Date date) {
		this.date = date;
	}

	public int getOverallBasketPrice() {
		return overallBasketPrice;
	}

	public void setOverallBasketPrice(int overallBasketPrice) {
		this.overallBasketPrice = overallBasketPrice;
	}

}