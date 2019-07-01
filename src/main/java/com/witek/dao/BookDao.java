package com.witek.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.witek.model.Book;

@Repository
public interface BookDao extends JpaRepository<Book, Long> {

	
	
}
