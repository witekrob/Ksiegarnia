package com.witek.model;
import com.witek.model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Constraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.persistence.JoinColumn;
@Entity
public class Client {

@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private int client_id;
@NotNull
@Size(min=3,max=20)
private String name;
@NotNull
@Size(min=3,max=20)
private String surname;
@Email
@NotNull
private String email;
@Size(min=3,max=20)
private String password;
private Role role;

@OneToMany(cascade = CascadeType.ALL)
private List<Basket> basketHistory;

public Client() {}
public Client(String name, String surname, String email, String password, int client_id, List<Basket> basketHistory, Role role) {
	this.client_id=client_id;
	this.email=email;
	this.name=name;
	this.surname=surname;
	this.password=password;
	this.basketHistory=basketHistory;
	this.role=role;
}

public int getClient_id() {
	return client_id;
}
public void setClient_id(int client_id) {
	this.client_id = client_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSurname() {
	return surname;
}
public void setSurname(String surname) {
	this.surname = surname;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + client_id;
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
	Client other = (Client) obj;
	if (client_id != other.client_id)
		return false;
	if (email == null) {
		if (other.email != null)
			return false;
	} else if (!email.equals(other.email))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (password == null) {
		if (other.password != null)
			return false;
	} else if (!password.equals(other.password))
		return false;
	if (surname == null) {
		if (other.surname != null)
			return false;
	} else if (!surname.equals(other.surname))
		return false;
	return true;
}
@Override
public String toString() {
	return "Client [client_id=" + client_id + ", name=" + name + ", surname=" + surname + ", email=" + email
			+ ", password=" + password + ", role = " + role + "  ]";
}
public List<Basket> getBasketHistory() {
	return basketHistory;
}
public void setBasketHistory(List<Basket> basketHistory) {
	this.basketHistory = basketHistory;
}
public Role getRole() {
	return role;
}
public void setRole(Role role) {
	this.role = role;
}
	


}
