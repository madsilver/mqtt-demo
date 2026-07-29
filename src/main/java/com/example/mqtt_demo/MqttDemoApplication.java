package com.example.mqtt_demo;

import com.example.mqtt_demo.config.MqttProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.integration.config.EnableIntegration;

@EnableIntegration
@SpringBootApplication
@EnableConfigurationProperties(MqttProperties.class)
public class MqttDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttDemoApplication.class, args);
	}

}
