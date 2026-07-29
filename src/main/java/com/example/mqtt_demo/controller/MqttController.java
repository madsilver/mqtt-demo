package com.example.mqtt_demo.controller;

import com.example.mqtt_demo.service.MqttPublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private final MqttPublisherService publisherService;

    public MqttController(MqttPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> publish(@RequestBody String message) {
        publisherService.publish(message);
        return ResponseEntity.accepted().build();
    }
}