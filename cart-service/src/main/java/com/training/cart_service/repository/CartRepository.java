package com.training.cart_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.cart_service.model.CartItem;

public interface CartRepository extends JpaRepository<CartItem, Long>{
	List<CartItem> findByUserId(Long userId);
	CartItem findByUserIdAndProductId(Long userId, Long productId);

}
