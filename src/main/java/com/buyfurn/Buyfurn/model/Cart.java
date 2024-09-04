package com.buyfurn.Buyfurn.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;


@Entity
public class Cart {
	@Id
	private String id;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
	List<Product> products = new ArrayList<Product>();
	
	 @PrePersist
	    public void generateId() {
	        if (this.id == null) {
	            this.id = UUID.randomUUID().toString();
	        }
	    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
