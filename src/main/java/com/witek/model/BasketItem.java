package com.witek.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BasketItem {

	
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int basketItem_id;
@OneToOne(targetEntity=Book.class)
private Book book;
private int quantity;
private int price;
@ManyToOne (cascade=CascadeType.ALL)
private Basket basket;
public BasketItem() {}
public BasketItem(int basketItem_id,Book book, int quantity, int price) {
	this.basketItem_id=basketItem_id;
	this.book = book;
	this.quantity= quantity;
	this.price=price;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + basketItem_id;
	result = prime * result + ((book == null) ? 0 : book.hashCode());
	result = prime * result + price;
	result = prime * result + quantity;
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
	BasketItem other = (BasketItem) obj;
	if (basketItem_id != other.basketItem_id)
		return false;
	if (book == null) {
		if (other.book != null)
			return false;
	} else if (!book.equals(other.book))
		return false;
	if (price != other.price)
		return false;
	if (quantity != other.quantity)
		return false;
	return true;
}
@Override
public String toString() {
	return "BasketItem [id=" + basketItem_id + ", quantity=" + quantity + ", price="
			+ price  + book + "]";
}
public Book getBook() {
	return book;
}
public void setBook(Book book) {
	this.book = book;
}
public int getQuantity() {
	return quantity;
}
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
public int getBasketItem_id() {
	return basketItem_id;
}
public void setBasketItem_id(int basketItem_id) {
	this.basketItem_id = basketItem_id;
}
public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
}
