package com.example.demo.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ref;					// Referencia
	private String name;				// Nombre
	private Double price;     			// Precio
	private String imageUrl;			// Imagen
	private int amount;					// Cantidad
	
	@OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
	private List<OrderProduct> listaDePedidos;
	
	public Product() {
		super();
	}
	/**
	 * Constructor de producto
	 * @param name Nombre
	 * @param price Precio
	 * @param imageUrl Imagen
	 * @param amount Cantidad
	 */
	public Product(String name, Double price, String imageUrl, int amount) {
		super();
		this.name = name;
		this.price = price;
		this.imageUrl = imageUrl;
		this.amount = amount;
	}
	/**
	 * Constructor por referencia
	 * @param ref Referencia
	 */
	public Product(int ref) {
		this.ref = ref;
	}
	/**
	 * Obtiene la imagen
	 * @return String imagen
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * Obtiene la cantidad
	 * @return int Cantidad
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * Establece la cantidad
	 * @param amount Cantidad
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * Obtiene el nombre
	 * @return String nombre
	 */
	public String getName() {
		return name;
	}
	/**
	 * Obtiene el precio
	 * @return Double precio
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * Obtiene la referencia
	 * @return int referencia
	 */
	public int getRef() {
		return ref;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ref);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return ref == other.ref;
	}

	@Override
	public String toString() {
		return "Product [ref=" + ref + ", name=" + name + ", price=" + price + " y "+ amount + "]";
	}
	
	
}
