package com.witek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.witek.dao.ClientDao;
import com.witek.model.Basket;
import com.witek.model.Client;

@Service
public class ClientService {
	private ClientDao clientDao;
	private Client client;

	@Autowired
	public ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public void addNewClient(Client client) {
		String email = client.getEmail();
		Client check = clientDao.findByEmail(email);
		if (check == null) {
			clientDao.save(client);
		}
	}

	public Client login(String email, String password) {
		Client client = clientDao.findByEmailAndPassword(email,password);
		System.out.println(client);
		if (client != null) {
				this.client = client;
				return client;
		}
		return null;
	}

	public Client getClient() {
		return client;
	}

}
