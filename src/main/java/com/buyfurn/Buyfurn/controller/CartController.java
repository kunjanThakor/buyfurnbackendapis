package com.buyfurn.Buyfurn.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buyfurn.Buyfurn.model.Cart;
import com.buyfurn.Buyfurn.model.Product;
import com.buyfurn.Buyfurn.model.User;
import com.buyfurn.Buyfurn.service.CartServices;
import com.buyfurn.Buyfurn.service.ProductServices;
import com.buyfurn.Buyfurn.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CartController {

	 @Autowired
	    private UserService userService;

	    @Autowired
	    private CartServices cartService;
	    
	    @Autowired
	    private ProductServices productService;

	    @GetMapping("/{userId}/check-cart")
	    public ResponseEntity<String> checkAndCreateCart(@PathVariable Long userId) {
	        try {
	            User user = userService.getUserById(userId);
	            if (user == null) {
	                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	            }
	            
	            if (user.getCart() == null) {
	                Cart newCart = new Cart(); // Initialize your Cart object as needed
	                user.setCart(newCart);
	                cartService.createCart(newCart);
	                userService.updateUserCart(user); // Assuming you have an updateUser method
	                return new ResponseEntity<>("Cart created for user", HttpStatus.CREATED);
	            } else {
	                return new ResponseEntity<>("User already has a cart", HttpStatus.OK);
	            }
	        } catch (Exception e) {
	            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	    
	    @PostMapping("/addToCart/{id}")
	    public ResponseEntity<String> addToCart(@PathVariable Long id, Principal principal) {
	        try {
	            User user = userService.getUser(principal.getName());
	            if (user == null) {
	                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	            }
	            
	            Product product = productService.getById(id);
	            if (product == null) {
	                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	            }
	            
	            cartService.addToCart(user, product);
	            return new ResponseEntity<>("Product added to cart", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	    @GetMapping("/cart/products")
	    public ResponseEntity<List<Product>> getCartProducts(Principal principal) {
	        try {
	            User user = userService.getUser(principal.getName());
	            if (user == null) {
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }

	            Cart cart = user.getCart();
	            if (cart == null || cart.getProducts().isEmpty()) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No products in the cart
	            }

	            List<Product> products = cart.getProducts();
	            return new ResponseEntity<>(products, HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	    @PostMapping("/addOrRemoveFromCart/{id}")
	    public ResponseEntity<String> addOrRemoveFromCart(@PathVariable Long id, Principal principal) {
	        try {
	            User user = userService.getUser(principal.getName());
	            if (user == null) {
	                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	            }

	            Product product = productService.getById(id);  // Fetch product from the database
	            if (product == null) {
	                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	            }

	            Cart cart = user.getCart();
	            if (cart == null) {
	                return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
	            }

	            // Ensure you're working with the same instance of the product
	            if (cart.getProducts().contains(product)) {
	                cart.getProducts().remove(product);  // Remove product from cart
	            } else {
	                cart.getProducts().add(product);  // Add product to cart
	            }

	            // Use CartService to update the cart
	            cartService.updateCart(cart);  // Save the updated cart

	            return new ResponseEntity<>("Cart updated", HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

}
