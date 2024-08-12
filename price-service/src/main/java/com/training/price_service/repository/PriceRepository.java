package com.training.price_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.price_service.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long>{
	Price findByProductId(Long productId);
	void deleteByProductId(Long productId);
}
