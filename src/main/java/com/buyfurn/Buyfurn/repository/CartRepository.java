package com.buyfurn.Buyfurn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyfurn.Buyfurn.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

}
