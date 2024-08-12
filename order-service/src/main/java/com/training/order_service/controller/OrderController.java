package com.training.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.training.order_service.model.Orders;
import com.training.order_service.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/checkout")
	public ResponseEntity<Orders> checkout(@RequestParam Long userId){
		Orders order = orderService.checkout(userId);
		return ResponseEntity.ok(order);
	}
}
