# mqtt-demo

## Flow
```
                +----------------------+
                |     Mosquitto        |
                +----------+-----------+
                           |
                           | demo/messages
                           |
        +------------------v------------------+
        | MqttPahoMessageDrivenChannelAdapter |
        +------------------+------------------+
                           |
                           | Message<?>
                           |
                 +---------v---------+
                 | mqttInputChannel  |
                 +---------+---------+
                           |
                           |
                 +---------v---------+
                 | @ServiceActivator |
                 +---------+---------+
                           |
                           |
                 System.out.println()
```

## Run
```
mvn spring-boot:run
```

## Publish message
```sh
docker exec -it mosquitto sh
```

```sh
mosquitto_pub \
    -h localhost \
    -t demo/messages \
    -m "Hello Spring MQTT!"
```

```
Message received: Olá Spring MQTT!
Headers: {
    mqtt_receivedRetained=false, 
    mqtt_id=0, 
    mqtt_duplicate=false, 
    id=d4b3a43a-7b6a-3976-8fd9-f886a8ed5fe6, 
    mqtt_receivedTopic=demo/messages, 
    mqtt_receivedQos=0, 
    timestamp=1785327709551
}
```

## Publish Flow
```
HTTP POST
     |
@RestController
     |
mqttOutputChannel
     |
MqttPahoMessageHandler
     |
Mosquitto
```

```
HTTP POST
      |
MqttController
      |
MqttPublisherService
      |
mqttOutputChannel
      |
MqttPahoMessageHandler
      |
Mosquitto
      |
MqttPahoMessageDrivenChannelAdapter
      |
mqttInputChannel
      |
MqttMessageHandler
```

```sh
curl -X POST http://localhost:8080/mqtt/publish -d "Hello MQTT"
```