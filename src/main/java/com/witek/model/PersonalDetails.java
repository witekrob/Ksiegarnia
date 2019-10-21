package com.witek.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class PersonalDetails {


@Id
private int telNumber;	
private String city;
private String street;
private int postCode;
@OneToOne(cascade = CascadeType.ALL)
private Client client;

@Autowired
public PersonalDetails(String city, String street, int postCode, int telNumber, Client client) {
	this.city=city;
	this.street=street;
	this.postCode=postCode;
	this.telNumber=telNumber;
	this.client=client;
}
public PersonalDetails() {}

public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}
public int getPostCode() {
	return postCode;
}
public void setPostCode(int postCode) {
	this.postCode = postCode;
}
public int getTelNumber() {
	return telNumber;
}
public void setTelNumber(int telNumber) {
	this.telNumber = telNumber;
}
public Client getClient() {
	return client;
}
public void setClient(Client client) {
	this.client = client;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((city == null) ? 0 : city.hashCode());
	result = prime * result + ((client == null) ? 0 : client.hashCode());
	result = prime * result + postCode;
	result = prime * result + ((street == null) ? 0 : street.hashCode());
	result = prime * result + telNumber;
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	PersonalDetails other = (PersonalDetails) obj;
	if (city == null) {
		if (other.city != null)
			return false;
	} else if (!city.equals(other.city))
		return false;
	if (client == null) {
		if (other.client != null)
			return false;
	} else if (!client.equals(other.client))
		return false;
	if (postCode != other.postCode)
		return false;
	if (street == null) {
		if (other.street != null)
			return false;
	} else if (!street.equals(other.street))
		return false;
	if (telNumber != other.telNumber)
		return false;
	return true;
}
@Override
public String toString() {
	return "PersonalDetails [city=" + city + ", street=" + street + ", postCode=" + postCode + ", telNumber="
			+ telNumber + ", client=" + client + "]";
}
}