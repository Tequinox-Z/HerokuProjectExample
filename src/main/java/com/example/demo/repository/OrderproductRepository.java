package com.example.demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;
import com.example.demo.model.OrderProduct;
import com.example.demo.model.Product;
@Repository
public interface OrderproductRepository extends JpaRepository<OrderProduct, Long> {
	/**
	 * Obtiene una linea de pedido mediante un pedido y producto determinado 
	 * @param pedido
	 * @param producto
	 * @return
	 */
	@Query("select o from OrderProduct o where o.pedido like ?1 AND o.producto like ?2")
	OrderProduct obtenerLineaDePedidoPorPedidoYProducto(Order pedido, Product producto);
}
