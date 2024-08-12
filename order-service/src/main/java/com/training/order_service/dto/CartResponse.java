package com.training.order_service.dto;

import java.util.List;
import java.util.Map;

public class CartResponse {

	private List<Map<String, Object>> items;

	public List<Map<String, Object>> getItems() {
		return items;
	}

	public void setItems(List<Map<String, Object>> items) {
		this.items = items;
	}
}
