package com.witek.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.witek.model.PersonalDetails;

@Repository
public interface DetailsDao extends CrudRepository<PersonalDetails, Long> {

}
