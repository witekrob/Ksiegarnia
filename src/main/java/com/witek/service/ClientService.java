package com.witek.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.witek.dao.ClientDao;
import com.witek.model.Basket;
import com.witek.model.BasketItem;
import com.witek.model.Client;
import com.witek.model.Role;

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
		System.out.println(client.getPersonalDetails());
		
		Client check = clientDao.findByEmail(email);
		if (check == null) {
			if (client.getRole()==null) {
				client.setRole(Role.CLIENT);
			}
		}
		clientDao.save(client);
		
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

	public List<Basket> getOrderHistory(Client client){
		client = clientDao.findByEmail(client.getEmail());
		List<Basket> orderHistory = client.getBasketHistory();
		System.out.println(orderHistory);
		return orderHistory;
	}


	public void logout(HttpServletRequest request) {
		request.getSession().invalidate();
		System.out.println("log out");
	}
	public Client getClient(HttpServletRequest request) {
		Client client = (Client)request.getSession().getAttribute("client");
		return client;
	}
}
