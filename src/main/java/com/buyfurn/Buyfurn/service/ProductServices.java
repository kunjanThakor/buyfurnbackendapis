package com.buyfurn.Buyfurn.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public List<ProductImages> uploadImages(MultipartFile[] imgs) throws IOException {

		List<ProductImages> productImages = new ArrayList<ProductImages>();

		for (int i = 0; i < imgs.length; i++) {
			ProductImages images = new ProductImages(imgs[i].getOriginalFilename(), imgs[i].getContentType(),
					imgs[i].getBytes());
			productImages.add(images);
		}

		return productImages;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getById(Long id) {
		return productRepository.findById(id).get();
	}

	public Product updateProduct(Product product, MultipartFile[] image) throws IOException {

	    if (image == null) {
	        image = new MultipartFile[0];
	    }

	    Product newProduct = productRepository.findById(product.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
	    newProduct.setTitle(product.getTitle());
	    newProduct.setColor(product.getColor());
	    newProduct.setCareAndMaintenance(product.getCareAndMaintenance());
	    newProduct.setWeight(product.getWeight());
	    newProduct.setDescription(product.getDescription());
	    newProduct.setSeatingCapacity(product.getSeatingCapacity());
	    newProduct.setPrice(product.getPrice());
	    newProduct.setMaterial(product.getMaterial());
	    newProduct.setStockStatus(product.getStockStatus());
	    newProduct.setCategory(product.getCategory());
	    newProduct.setWarranty(product.getWarranty());

	    if (image.length > 0) {
	        newProduct.setProductImages(uploadImages(image));
	    }

	    productRepository.save(newProduct);
	    return newProduct; // Return the updated product
	}

	public String deleteById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return "Product Deleted !!";
        } else {
            return "Product not found !!";
        }
    }
}
