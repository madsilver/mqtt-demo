package com.example.mqtt_demo.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {

    @Bean
    public MqttPahoClientFactory mqttClientFactory(MqttProperties properties) {
        var connectionOptions = new MqttConnectOptions();

        connectionOptions.setServerURIs(
                new String[]{properties.brokerUrl()}
        );
        connectionOptions.setAutomaticReconnect(true);
        connectionOptions.setCleanSession(true);

        var clientFactory = new DefaultMqttPahoClientFactory();
        clientFactory.setConnectionOptions(connectionOptions);

        return clientFactory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer mqttInbound(
            MqttPahoClientFactory clientFactory,
            MqttProperties properties,
            MessageChannel mqttInputChannel
    ) {
        var adapter = new MqttPahoMessageDrivenChannelAdapter(
                properties.clientId(),
                clientFactory,
                properties.topic()
        );

        adapter.setCompletionTimeout(5_000);
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel);

        return adapter;
    }

    @Bean
    public MessageChannel mqttOutputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutputChannel")
    public MessageHandler mqttOutbound(
            MqttPahoClientFactory clientFactory,
            MqttProperties properties
    ) {
        var handler = new MqttPahoMessageHandler(
                properties.clientId() + "-publisher",
                clientFactory
        );

        handler.setAsync(true);
        handler.setDefaultTopic(properties.topic());
        handler.setDefaultQos(1);

        return handler;
    }
}