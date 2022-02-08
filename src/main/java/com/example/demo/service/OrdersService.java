package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderProduct;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderproductRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class OrdersService {
	
	// =========================== Repositorios ===========================
	
	@Autowired
	private UsuarioRepository usersRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderproductRepository orderProductRepository;
	
	// ============================ Servicios ============================
	
	@Autowired
	private UsersService usersService;
	
	/**
	 * Obtiene los pedidos de un usuario
	 * @param username Usuario
	 * @return List de pedidos
	 */
	public List<Order> getOrdersByUsername(String username) {
		return usersService.getOrders(username);
	}
	/**
	 * Añade un pedido a un usuario
	 * @param username Usuario
	 * @param order Pedido
	 */
	public void addOrder(String username, Order order) {
		orderRepository.save(order);
		
		User user = usersService.getByUsername(username);
		user.getOrders().add(order);
		
		usersRepository.save(user);
	}
	/**
	 * Comprueba su existe un pedido
	 * @param username Usuario
	 * @param id Id
	 * @return Está el pedido?
	 */
	public boolean existsOrder(String username, int id) {
		return this.getOrdersByUsername(username).contains(new Order(id));
	}
	/**
	 * Obtiene el pedido por su id
	 * @param username Usuario
	 * @param orderId Id
	 * @return Pedido
	 */
	public Order getOrderById(String username, int orderId) {
		List<Order> orders = this.getOrdersByUsername(username);
		
		return orders.get(orders.indexOf(new Order(orderId)));
	}
	/**
	 * Borra un pedido
	 * @param username Usuario
	 * @param order Pedido
	 */
	public void remove(String username, Order order) {
		User user = usersService.getByUsername(username);
		
		for (OrderProduct currentOrderProduct: order.getListDePedidos()) {
			Product producto = currentOrderProduct.getProducto();
			producto.setAmount(producto.getAmount() + currentOrderProduct.getCantidad());
		}
		orderProductRepository.deleteAll(order.getListDePedidos());
		
		user.getOrders().remove(order);
		usersRepository.save(user);
		orderRepository.delete(order);
	}

	/**
	 * Edita un pedido
	 * @param username Usuario
	 * @param order Pedido
	 */
	public void edit(String username, Order newOrder, Order oldOrder) {				
		newOrder.setId(oldOrder.getId());
		newOrder.setDate(new Date());
		orderRepository.save(newOrder);
	}

	/**
	 * Obtiene el precio del pedido
	 * @param products Productos
	 * @return Double cantidad
	 */
	public Double getTotal(Order order) {
		double totalAmount = 0;
		for (OrderProduct orderProduct: order.getListDePedidos()) {
			totalAmount += orderProduct.getProducto().getPrice() * orderProduct.getCantidad();
		}
		return totalAmount;
	}
}
