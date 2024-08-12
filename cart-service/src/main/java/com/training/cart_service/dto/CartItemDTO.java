package com.training.cart_service.dto;

import jakarta.validation.constraints.NotNull;

public class CartItemDTO {
	
	@NotNull
	private Long userId;
	@NotNull
	private Long productId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
