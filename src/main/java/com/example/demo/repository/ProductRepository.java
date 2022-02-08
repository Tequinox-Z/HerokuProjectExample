package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;
@Repository

public interface ProductRepository extends JpaRepository<Product, Integer> {
	/**
	 * Obtiene los productos cuyo nombre coincida con lo indicado
	 * @param query
	 * @return
	 */
	@Query("select p from Product p where lower(p.name) like %?1%")
	public List<Product> query(String query);
}
