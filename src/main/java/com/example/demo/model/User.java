package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	private String username;
	private String password;
	private String fullName;
	private String telefone;
	private String email;
	private String address;
	
	public User() {
		super();
	}
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Order> orders;
	
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	/**
	 * Constructor para usuario
	 * @param username Nombre de usuario
	 * @param password Contraseña
	 * @param fullName Nombre
	 * @param telefone Teléfono
	 * @param address Dirección
	 * @param email Correo
	 */
	public User(String username, String password, String fullName, String telefone, String address, String email) {
		super();
		this.orders =  new ArrayList<>();
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.telefone = telefone;
		this.address = address;
		this.email = email;
	}
	/**
	 * Constructor de usuario
	 * @param username
	 */
	public User(String username) {
		super();
		this.username = username;
	}
	/**
	 * Obtiene el nombre de usuario
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Obtiene los pedidos
	 * @return
	 */
	public List<Order> getOrders() {
		return orders;
	}
	/**
	 * Comprueba la contraseña
	 * @param password Contraseña
	 * @return Login?
	 */
	public boolean isPskValid(String password) {
		return this.password.equals(password.trim());
	}
	/**
	 * Obtiene el nombre completo
	 * @return String nombre
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * Obtiene el teléfono
	 * @return String teléfono
	 */
	public String getTelefone() {
		return telefone;
	}
	/**
	 * Obtiene la dirección
	 * @return String dirección
	 */
	public String getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", fullName=" + fullName + ", telefone="
				+ telefone + ", address=" + address + "]";
	}
	/**
	 * Obtiene el correo
	 * @return String correo
	 */
	public String getEmail() {
		return email;
	}
	
}
