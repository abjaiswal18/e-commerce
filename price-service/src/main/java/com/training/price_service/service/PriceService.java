package com.training.price_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.training.price_service.exception.ResourceNotFoundException;
import com.training.price_service.model.Price;
import com.training.price_service.repository.PriceRepository;

@Service
public class PriceService {
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String productServiceUrl = "http://product-service/products/";
	
	public Price setPrice(Long productId, Double price) {
		Price priceEntry = priceRepository.findByProductId(productId);
		if(priceEntry == null) {
			priceEntry = new Price();
			priceEntry.setProductId(productId);
		}
		priceEntry.setPrice(price);
		return priceRepository.save(priceEntry);
	}
	
	public Double getPrice(Long productId, boolean fromProductService) {
		if(!fromProductService) {
			String url = productServiceUrl + productId;
			restTemplate.getForObject(url, String.class);
		}
		Price priceEntry = priceRepository.findByProductId(productId);
		if(priceEntry == null) {
			throw new ResourceNotFoundException("Price not found for product ID: " + productId);
		}
		return priceEntry.getPrice();
	}
	
	@Transactional
	public void deletePrice(Long productId) {
		priceRepository.deleteByProductId(productId);
	}
	
}
