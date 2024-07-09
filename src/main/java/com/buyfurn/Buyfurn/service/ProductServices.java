package com.buyfurn.Buyfurn.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.buyfurn.Buyfurn.model.Product;
import com.buyfurn.Buyfurn.model.ProductImages;
import com.buyfurn.Buyfurn.repository.ProductRepository;

@Service
public class ProductServices {

	@Autowired
	ProductRepository productRepository;

	public Product addProduct(Product product, MultipartFile[] images) throws IOException {
		product.setProductImages(uploadImages(images));
		productRepository.save(product);
		return product;
	}
	
	public List<ProductImages> uploadImages(MultipartFile[] imgs) throws IOException{
		
		List<ProductImages> productImages= new ArrayList<ProductImages>();
		
		for (int i = 0; i < imgs.length; i++) {
			ProductImages images  = new ProductImages(imgs[i].getOriginalFilename(), imgs[i].getContentType(), imgs[i].getBytes());
			productImages.add(images);
		}
		
		return productImages;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).get();
	}

	public Product updateProduct(Product product, MultipartFile[] image) throws IOException {

		Product newProduct = productRepository.findById(product.getId()).get();
		newProduct.setProductImages(uploadImages(image));
		productRepository.save(newProduct);
		return product;
	}

	public String deleteById(Long id) {
		productRepository.deleteById(id);
		return "Product Deleted !!";
	}
}
