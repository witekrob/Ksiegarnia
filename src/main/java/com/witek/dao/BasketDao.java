package com.witek.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.witek.model.Basket;
@Repository
public interface BasketDao extends CrudRepository<Basket, Long> {

}
