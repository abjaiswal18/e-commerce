package com.training.cart_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.training.cart_service.exception.CartEmptyException;
import com.training.cart_service.exception.CartItemNotFoundException;
import com.training.cart_service.model.CartItem;
import com.training.cart_service.repository.CartRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static final String productServiceUrl = "http://product-service/products/";
	
	public CartItem addProductToCart(Long userId, Long productId, int quantity) {
		CartItem cartItem = cartRepository.findByUserIdAndProductId(userId, productId);
		if(cartItem == null) {
			cartItem = new CartItem();
			cartItem.setUserId(userId);
			restTemplate.getForObject(productServiceUrl + productId, String.class);
			cartItem.setProductId(productId);
			cartItem.setQuantity(quantity);
		} else {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		}
		return cartRepository.save(cartItem);
	}
	
	public List<CartItem> getCartItems(Long userId){
		List<CartItem> cartItems = cartRepository.findByUserId(userId);
		if(cartItems.isEmpty()) {
			throw new CartEmptyException("The cart is empty");
		}
		return cartItems;
	}
	
	public void removeProductFromCart(Long userId, Long productId) {
		CartItem cartItem = cartRepository.findByUserIdAndProductId(userId, productId);
		if(cartItem == null) {
			throw new CartItemNotFoundException("Product not found in cart");
		}
		cartRepository.delete(cartItem);
	}

	public void clearCart(Long userId) {
		List<CartItem> cartItem = cartRepository.findByUserId(userId);
		if(cartItem == null) {
			throw new CartItemNotFoundException("Product not found in cart");
		}
		cartRepository.deleteAll(cartItem);
		
	}
}
