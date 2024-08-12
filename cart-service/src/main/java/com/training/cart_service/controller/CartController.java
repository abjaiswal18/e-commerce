package com.training.cart_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.cart_service.dto.CartItemDTO;
import com.training.cart_service.model.CartItem;
import com.training.cart_service.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@PostMapping("/add")
	public ResponseEntity<CartItem> addProductToCart( @Valid @RequestBody CartItem cart){
		CartItem cartItem = cartService.addProductToCart(cart.getUserId(), cart.getProductId(), cart.getQuantity());
		return ResponseEntity.ok(cartItem);
	}
	
	@GetMapping("/items")
	public ResponseEntity<List<CartItem>> getCartItems(@RequestParam Long userId){
		List<CartItem> cartItems = cartService.getCartItems(userId);
		return ResponseEntity.ok(cartItems);
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<Void> removeProductFromCart(@Valid @RequestBody CartItemDTO cart){
		cartService.removeProductFromCart(cart.getUserId(), cart.getProductId());
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/clear")
	public ResponseEntity<Void> clearCart(@RequestParam Long userId){
		cartService.clearCart(userId);
		return ResponseEntity.noContent().build();
	}
}
