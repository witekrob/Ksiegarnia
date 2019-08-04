package com.witek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.witek.dao.AuthorDao;
import com.witek.model.Author;
import com.witek.model.Book;

@Service
public class AuthorService {

	private AuthorDao authorDao;

	@Autowired
	public AuthorService(AuthorDao authorDao) {
		this.authorDao = authorDao;
	}
public List<Author> getAll (){
	List<Author> allAuthors = authorDao.findAll();
	return allAuthors;
}
public void addNewAuthor(Author author) {
	authorDao.save(author);
}
public List<Author> findBySurname(String surname) {
	List<Author> bySurname = authorDao.findBySurname(surname);
	return bySurname;
}
public List<Author> findByCountry(String country) {
	List<Author> byCountry = authorDao.findByCountry(country);
	return byCountry;
}
public Author checkIfExist(Author author) {
	Author isThereAny = authorDao.findBySurnameAndYearOfBirth(author.getSurname(), author.getYearOfBirth());
	return isThereAny;
}
public Author getById(Long author_id) {
	Author foundById = authorDao.findById(author_id).get();
	return foundById;
}
public Author editAuthor(Author newDetailsAuthor, Author authorToEdit) {
	if (newDetailsAuthor.getCountry() != "") {
		authorToEdit.setCountry(newDetailsAuthor.getCountry());
	}
	if (newDetailsAuthor.getName() != "") {
		authorToEdit.setName(newDetailsAuthor.getName());
	}
	if (newDetailsAuthor.getSurname() != "") {
		authorToEdit.setSurname(newDetailsAuthor.getSurname());
	}
	if (newDetailsAuthor.getYearOfBirth() != 0) {
		authorToEdit.setYearOfBirth(newDetailsAuthor.getYearOfBirth());
	}
	this.addNewAuthor(authorToEdit);
	return authorToEdit;
}
	
}
