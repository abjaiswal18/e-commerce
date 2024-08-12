package com.training.notification_service.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	public void notifyUser(String message) {
		System.out.println("Notification to user: " + message);
	}
}
