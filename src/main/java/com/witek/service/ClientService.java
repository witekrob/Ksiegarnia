package com.witek.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.witek.dao.ClientDao;
import com.witek.model.Basket;
import com.witek.model.Client;

@Service
@Transactional
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
	public void savuj (Client client) {
		clientDao.save(client);
	}
	public List<Basket> getOrderHistory(Client client){
		client = clientDao.findByEmail(client.getEmail());
		List<Basket> orderHistory = client.getBasketHistory();
		return orderHistory;
	}
}
