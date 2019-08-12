package com.witek.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.engine.internal.Cascade;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_number;
	private String title;
	@ManyToOne (cascade=CascadeType.ALL)
	private Author author;
	private int pages;
	private int price;
	private int quantity;

	public Book() {
	}

	public Book(Long id_number,String title, Author author, int price, int pages, int quantity) {
		this.id_number= id_number;
		this.title = title;
		this.author = author;
		this.pages = pages;
		this.price=price;
		this.quantity=quantity;
	}

	@Override
	public String toString() {
		return "Book [id_number=" + id_number + ", title=" + title + ", author=" + author + ", pages=" + pages
				+ ", price=" + price + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((id_number == null) ? 0 : id_number.hashCode());
		result = prime * result + pages;
		result = prime * result + price;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (id_number == null) {
			if (other.id_number != null)
				return false;
		} else if (!id_number.equals(other.id_number))
			return false;
		if (pages != other.pages)
			return false;
		if (price != other.price)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public Long getId_number() {
		return id_number;
	}

	public void setId_number(Long id_number) {
		this.id_number = id_number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
