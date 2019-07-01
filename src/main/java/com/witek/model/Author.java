package com.witek.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity

public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int author_id;
	private String name;
	private String surname;
	private String country;
	private int yearOfBirth;
	@OneToMany(cascade=CascadeType.ALL)
	private List<Book> books = new ArrayList<Book>();

	public Author() {
	}

	public Author(String name, String surname, String country, int yearOfBirth, List<Book> books) {
		this.name = name;
		this.surname = surname;
		this.country = country;
		this.yearOfBirth = yearOfBirth;
		this.books = books;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + author_id;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + yearOfBirth;
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
		Author other = (Author) obj;
		if (author_id != other.author_id)
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (yearOfBirth != other.yearOfBirth)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Author [author_id=" + author_id + ", name=" + name + ", surname=" + surname + ", country=" + country
				+ ", yearOfBirth=" + yearOfBirth +  "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
