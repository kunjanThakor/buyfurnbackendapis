package com.buyfurn.Buyfurn.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.buyfurn.Buyfurn.model.Product;
import com.buyfurn.Buyfurn.service.ProductServices;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ProductController {

	@Autowired
	ProductServices productServices;
	
	 @PostMapping(value = "/admin/addproduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public Product addProduct(@RequestPart("product") String productJson, @RequestPart("imgs") MultipartFile[] images) throws IOException {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Product product = objectMapper.readValue(productJson, Product.class);
	        return productServices.addProduct(product, images);
	}
	 
	 @GetMapping("/getallproducts")
	 public List<Product> getAllProducts(){
		 return productServices.getAllProducts();
	 }
}
