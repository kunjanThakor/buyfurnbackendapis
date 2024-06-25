package com.buyfurn.Buyfurn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyfurn.Buyfurn.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
