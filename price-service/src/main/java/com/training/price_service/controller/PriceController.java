package com.training.price_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.price_service.model.Price;
import com.training.price_service.service.PriceService;

@RestController
@RequestMapping("/prices")
public class PriceController {
	
	@Autowired
	private PriceService priceService;
	
	@PostMapping("/{productId}")
	public ResponseEntity<Price> setPrice(@PathVariable Long productId, @RequestBody Price price){
		Price updatedPrice = priceService.setPrice(productId, price.getPrice());
		return ResponseEntity.ok(updatedPrice);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<Double> getPrice(@PathVariable Long productId, @RequestParam(required = false) boolean fromProductService){
		Double price = priceService.getPrice(productId, fromProductService);
		return ResponseEntity.ok(price);
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<Void> deletePrice(@PathVariable Long productId){
		priceService.deletePrice(productId);
		return ResponseEntity.noContent().build();
	}
}
