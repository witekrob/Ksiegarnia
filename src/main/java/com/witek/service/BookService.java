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
public BookService (BookDao bookDao) {
	this.bookDao=bookDao;
}
public void saveBook (Book book) {
	bookDao.save(book);
}
public List<Book> getAllBooks() {
	List<Book> allBooks = bookDao.findAll();
	return allBooks;
}

}
