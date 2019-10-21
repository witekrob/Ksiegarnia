package com.witek.service;

import org.springframework.stereotype.Service;

import com.witek.model.Book;

@Service
public class adminService {
	
	
	public adminService(){}
	
	public void outOfStock (Book book) {
		System.out.println("Book :" + book.getTitle() + " " +  book.getAuthor().getSurname() + " OUT OF STOCK");
		
	}

}
