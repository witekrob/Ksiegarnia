package com.witek.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.witek.model.Client;
@Repository
public interface ClientDao extends CrudRepository<Client, Long> {
Client findByEmail(String email);

Client findByEmailAndPassword(String email, String password);
}
