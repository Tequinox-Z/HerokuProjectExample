package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Order pedido;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Product producto; 
	
	private int cantidad;
	
	public OrderProduct(Order pedido, Product producto, int cantidad) {
		super();
		this.pedido = pedido;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public OrderProduct(long id) {
		super();
		this.id = id;
	}

	public OrderProduct() {
		super();
	}

	public Order getPedido() {
		return pedido;
	}

	public void setPedido(Order pedido) {
		this.pedido = pedido;
	}

	public Product getProducto() {
		return producto;
	}

	public void setProducto(Product producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderProduct other = (OrderProduct) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "OrderProduct [id=" + id + ", pedido=" + pedido + ", producto=" + producto + ", cantidad=" + cantidad
				+ "]";
	}
}
