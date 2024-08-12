package com.training.product_detail_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.product_detail_service.model.ProductDetail;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>{
	ProductDetail findByProductId(Long productId);
	void deleteByProductId(Long productId);
}
