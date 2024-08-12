package com.training.order_service.exception;

public class OrderCreationException extends RuntimeException {
	public OrderCreationException(String message) {
		super(message);
	}
}
