package com.training.order_service.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.training.order_service.enums.OrderStatus;
import com.training.order_service.exception.OrderCreationException;
import com.training.order_service.model.Orders;
import com.training.order_service.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	private static final String cartServiceUrl = "http://cart-service/cart/";
	private static final String priceServiceUrl = "http://price-service/prices/";

	public Orders checkout(Long userId) {
		List<Map<String, Object>> cartItems = getCartItems(userId);

		Orders order = new Orders();
		order.setUserId(userId);
		order.setProductIds(extractProductIds(cartItems));
		order.setTotalPrice(calculateTotalPrice(order.getProductIds(), cartItems));
		order.setStatus(OrderStatus.PENDING);

		Orders savedOrder = orderRepository.save(order);

		try {
			processOrder(savedOrder);
		} catch (Exception e) {
			savedOrder.setStatus(OrderStatus.FAILED);
			orderRepository.save(savedOrder);
			throw new OrderCreationException("Order creation failed: " + e.getMessage());
		}
		savedOrder.setStatus(OrderStatus.COMPLETED);
		return orderRepository.save(savedOrder);
	}

	private Double calculateTotalPrice(List<Long> productIds, List<Map<String, Object>> cartItems) {
		double totalPrice = 0;
		for(Long id : productIds) {
			Double price = restTemplate.getForObject(priceServiceUrl + id, Double.class);
			Optional<Integer> quantity = cartItems.stream()
		            .filter(item -> id.equals(((Number) item.get("productId")).longValue()))
		            .map(item -> ((Number) item.get("quantity")).intValue())
		            .findFirst();
			totalPrice += quantity.map(q -> price * q).orElse(0.0);
		}
		return totalPrice;
		
	}

	private List<Map<String, Object>> getCartItems(Long userId){
		
		ResponseEntity<List<Map<String, Object>>> cartResponse = restTemplate.getForEntity(cartServiceUrl + "items?userId=" + userId,
				(Class<List<Map<String, Object>>>) (Class<?>) List.class);
		List<Map<String, Object>> cartItems = cartResponse.getBody();

		if (cartItems == null || cartItems.isEmpty()) {
			throw new OrderCreationException("Cart is empty, cannot checkout and create new order");
		}
		return cartItems;
	}
	
	private List<Long> extractProductIds(List<Map<String, Object>> cartItems){
		return cartItems.stream().map(item -> ((Number) item.get("productId")).longValue()).toList();
	}
	
	private void processOrder(Orders order) {
		kafkaTemplate.send("order-topic", "order-created: "+ order.getId());
		restTemplate.delete(cartServiceUrl + "/clear?userId=" +order.getUserId());
		kafkaTemplate.send("order-topic", "order completed:" + order.getId());
	}
}
