package com.witek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.witek.dao.AuthorDao;
import com.witek.model.Author;

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

public void editAuthor(String surname, Author newDetails) {
	Author toEdit = authorDao.findBySurname(surname).get(0);
	toEdit = newDetails;
	authorDao.save(toEdit);
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
	
}
