package com.witek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.witek.dao.BasketItemDao;
import com.witek.model.Basket;
import com.witek.model.BasketItem;

@Service
@Transactional
public class BasketItemService {
	private BasketItemDao basketItemDao;

	@Autowired

	public BasketItemService(BasketItemDao basketItemDao) {
		this.basketItemDao = basketItemDao;
	}
}
