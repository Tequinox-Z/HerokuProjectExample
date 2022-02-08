package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderProduct;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderproductRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
	
	// =========================== Repositorios ===========================

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderproductRepository orderProductRepository;
	@Autowired
	private OrderRepository orderRepository;
	
	// ============================ Servicios ============================

	@Autowired
	private OrdersService ordersService;
	
	/**
	 * Obtiene todos los productos
	 * @return List de productos
	 */
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	/**
	 * Obtiene los productos por su referencia
	 * @param  productRef Referencia
	 * @return Producto
	 */
	public Product getProductByRef(int productRef) {
		return productRepository.findById(productRef).orElse(null);
	}
	/**
	 * Comprueba si el producto existe
	 * @param productId Id del producto
	 * @return
	 */
	public boolean existProduct(int productId) {
		return this.getProductByRef(productId) != null;
	}
	/**
	 * Edita una cantidad
	 * @param idPedido
	 * @param refProducto
	 * @param amount
	 */
	public void editAmount(Integer idPedido, Integer refProducto, Integer amount) {
		OrderProduct orderProduct = orderProductRepository.obtenerLineaDePedidoPorPedidoYProducto(orderRepository.getById(idPedido), productRepository.getById(refProducto));
		
		Integer amountProduct = orderProduct.getCantidad() - amount;
		
		orderProduct.getProducto().setAmount(orderProduct.getProducto().getAmount() + amountProduct);
		
		orderProduct.setCantidad(amount);
		
		productRepository.save(orderProduct.getProducto());
		orderProductRepository.save(orderProduct);
}
	
	/**
	 * Añade un producto a un pedido
	 * @param username Usuario
	 * @param orderid Id del pedido
	 * @param productRef Referencia del producto
	 * @param amount Cantidad
	 */
	public void addProduct(String username, int orderId, int productRef, int amount) {
		Order order = ordersService.getOrderById(username, orderId);
		Product product = productRepository.getById(productRef);
		
		product.setAmount(product.getAmount() - amount);
		
		OrderProduct orderProduct = this.obtenerLineaDePedido(orderId, productRef); 
		
		if (orderProduct == null) {
			orderProduct = new OrderProduct(order, product, amount);
		}
		else {
			orderProduct.setCantidad(orderProduct.getCantidad() + amount);
		}
		orderProductRepository.save(orderProduct);
	}
	/**
	 * Comprueba si un pedido tiene un producto
	 * @param username Usuario
	 * @param orderId Id pedido
	 * @param productRef Referencia del producto
	 * @return Boolean
	 */
	public boolean existProductInOrder(int orderId, int productRef) {
		return  this.obtenerLineaDePedido(orderId, productRef) != null;
	}
	
	/**
	 * Obtiene una linea de pedido
	 * @param orderId
	 * @param productRef
	 * @return
	 */
	public OrderProduct obtenerLineaDePedido(int orderId, int productRef) {
		return orderProductRepository.obtenerLineaDePedidoPorPedidoYProducto(orderRepository.getById(orderId), productRepository.getById(productRef));
	}
	/**
	 * Borra el producto de un pedido
	 * @param username Usuario
	 * @param orderId PEdido
	 * @param productRef Referencia
	 */
	public void removeProductInOrder(int orderId, int productRef) {
		OrderProduct orderProduct = obtenerLineaDePedido(orderId, productRef);
		Product producto = orderProduct.getProducto();
		
		producto.setAmount(producto.getAmount() + orderProduct.getCantidad());
		
		orderProductRepository.delete(orderProduct);
	}
	/**
	 * Busca un producto determinado
	 * @param query
	 * @return
	 */
	public List<Product> searchProduct(String query) {
		return productRepository.query(query);
	}
	
}
