package com.witek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.witek.dao.DetailsDao;
import com.witek.model.Client;
import com.witek.model.PersonalDetails;

@Service

public class PersonalDetailsService {
private DetailsDao detailsDao;

@Autowired
public PersonalDetailsService(DetailsDao detailsDao) {
	this.detailsDao=detailsDao;
}
}
