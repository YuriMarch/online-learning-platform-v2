package com.distancelearning.course.publishers;

import com.distancelearning.course.dtos.NotificationCommandDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class NotificationCommandPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${distancelearning.broker.exchange.notificationCommandExchange}")
    private String notificationCommandExchange;

//    @Value(value = "${distancelearning.broker.key.notificationCommandKey")
//    private String notificationCommandKey;

    public void publishNotificationCommand(NotificationCommandDto notificationCommandDto){
        rabbitTemplate.convertAndSend(notificationCommandExchange, "", notificationCommandDto);
    }
}
