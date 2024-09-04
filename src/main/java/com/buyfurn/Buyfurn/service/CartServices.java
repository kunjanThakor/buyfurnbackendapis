package com.buyfurn.Buyfurn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.buyfurn.Buyfurn.model.Cart;
import com.buyfurn.Buyfurn.model.Product;
import com.buyfurn.Buyfurn.model.User;
import com.buyfurn.Buyfurn.repository.CartRepository;

@Service
public class CartServices {

	@Autowired
	CartRepository cartRepository;

	public void createCart(Cart newCart) {
		   cartRepository.save(newCart);
		  
		
	}

	public void addToCart(User user, Product product) {
        Cart cart = user.getCart();
        if (cart != null) {
            cart.getProducts().add(product);
            cartRepository.save(cart);
        }
    }
}
