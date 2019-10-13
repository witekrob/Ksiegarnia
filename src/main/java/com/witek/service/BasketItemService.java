package com.witek.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.witek.dao.BasketItemDao;
import com.witek.model.Basket;
import com.witek.model.BasketItem;

@Service
@Transactional
public class BasketItemService {
	@PersistenceContext
	EntityManager entityManager;
	private BasketItemDao basketItemDao;
	
	@Autowired

	public BasketItemService(BasketItemDao basketItemDao) {
		this.basketItemDao = basketItemDao;
	}
	
	public int  getLastId() {
		String que= "SELECT basket_item_id from basket_item";
		javax.persistence.Query query = entityManager.createNativeQuery(que);
		List<Integer> list = query.getResultList();
		int last = list.get(list.size()-1);
		System.out.println(last);
		return last++;
	}
}
