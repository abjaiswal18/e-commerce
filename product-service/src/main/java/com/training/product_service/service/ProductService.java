package com.training.product_service.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.training.product_service.exception.ProductNotFoundException;
import com.training.product_service.model.Product;
import com.training.product_service.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String productDetailsServiceUrl = "http://product-detail-service/products/";
	private static final String priceServiceUrl = "http://price-service/prices/";
	
	public Product addProduct(Product product) {
		return productRepository.save(product);
	}
	
	public void removeProduct(Long id) {
		String productDetailUrl = productDetailsServiceUrl + "{id}/details";
		restTemplate.delete(priceServiceUrl+id);
		restTemplate.delete(productDetailUrl, id);
		productRepository.deleteById(id);
	}
	
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	public Product getProductDetails(Long id) {
		Product product = getProduct(id);
		String productDetailUrl = productDetailsServiceUrl + "{id}/details?fromProductService=true";
		Map<String, Object> details = restTemplate.getForObject(productDetailUrl, Map.class, id);
		Double price = restTemplate.getForObject(priceServiceUrl + id + "?fromProductService=true", Double.class);
		
		if(product != null) {
			product.setSize(details.get("size").toString());
			product.setDesign(details.get("design").toString());
			product.setPrice(price);
		}
		return product;
	}
	
	public Product getProduct(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with id "+ id));
	}
	
	

}
