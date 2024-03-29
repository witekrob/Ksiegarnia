package com.witek.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.witek.model.Author;

@Repository
public interface AuthorDao extends JpaRepository<Author, Long> {
	
	List<Author> findBySurname(String surname);
	List<Author> findByCountry(String country);
	Author findBySurnameAndYearOfBirth(String surname, int yearOfBirth);	
}
