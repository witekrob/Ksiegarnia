package com.witek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.witek.dao.BookDao;
import com.witek.model.Book;

@Service
public class BookService {

	private BookDao bookDao;

	@Autowired
	public BookService(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void saveBook(Book book) {
		bookDao.save(book);
	}

	public List<Book> getAllBooks() {
		List<Book> allBooks = bookDao.findAll();
		return allBooks;
	}

	public Book getById(Long id) {
		Book foundById = bookDao.findById(id).get();
		return foundById;
	}

	public Book editBook(Book bookToEdit, Book newBookDetails) {
		System.out.println("service editBook START");
		if (newBookDetails.getTitle() != "") {
			bookToEdit.setTitle(newBookDetails.getTitle());
		}
		if (newBookDetails.getPrice() != 0) {
			bookToEdit.setPrice(newBookDetails.getPrice());
		}
		if (newBookDetails.getPages() != 0) {
			bookToEdit.setPages(newBookDetails.getPages());
		}
		if (newBookDetails.getPages() != 0) {
			bookToEdit.setQuantity(newBookDetails.getQuantity());
		}

		return bookToEdit;
	}
}
