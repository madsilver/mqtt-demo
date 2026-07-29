package com.example.mqtt_demo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService {

    private final MessageChannel mqttOutputChannel;

    public MqttPublisherService(
            @Qualifier("mqttOutputChannel") MessageChannel mqttOutputChannel
    ) {
        this.mqttOutputChannel = mqttOutputChannel;
    }

    public boolean publish(String payload) {
        return mqttOutputChannel.send(
                MessageBuilder
                        .withPayload(payload)
                        .build()
        );
    }
}