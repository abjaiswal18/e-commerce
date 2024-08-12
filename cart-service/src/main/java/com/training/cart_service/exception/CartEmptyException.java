package com.training.cart_service.exception;

public class CartEmptyException extends RuntimeException {
	public CartEmptyException(String message) {
		super(message);
	}
}
