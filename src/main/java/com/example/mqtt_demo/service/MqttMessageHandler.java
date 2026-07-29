package com.example.mqtt_demo.service;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MqttMessageHandler {

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handle(Message<?> message) {
        System.out.println("Message received: " + message.getPayload());
        System.out.println("Headers: " + message.getHeaders());
    }
}