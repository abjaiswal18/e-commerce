package com.training.notification_service.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.training.notification_service.service.NotificationService;

@Component
public class NotificationListener {

	@Autowired
	private NotificationService notificationService;
	
	@KafkaListener(topics = "order-topic", groupId = "order-service-group")
	public void listenCheckoutEvent(String message) {
		notificationService.notifyUser(message);
	}
}
