package com.training.product_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.product_service.model.Product;
import com.training.product_service.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
		Product createdProduct = productService.addProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}
	
	@DeleteMapping("/{id}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
		productService.removeProduct(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/details/{id}")
	public ResponseEntity<Product> getProductDetails(@PathVariable Long id){
		Product product = productService.getProductDetails(id);
		return ResponseEntity.ok(product);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable Long id){
		Product product = productService.getProduct(id);
		return ResponseEntity.ok(product);
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getAllProduct(){
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}
	
}
