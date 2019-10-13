package com.witek.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.witek.model.Author;
import com.witek.model.BasketItem;
@Repository
public interface BasketItemDao extends CrudRepository<BasketItem, Long>{
	BasketItem findByBookTitle(String title);
	
}
