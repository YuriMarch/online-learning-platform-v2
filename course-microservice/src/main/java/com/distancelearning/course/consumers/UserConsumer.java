package com.distancelearning.course.consumers;

import com.distancelearning.course.dtos.UserEventDto;
import com.distancelearning.course.enums.ActionType;
import com.distancelearning.course.models.UserModel;
import com.distancelearning.course.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class UserConsumer {

    @Autowired
    UserService userService;


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${distancelearning.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${distancelearning.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenUserEvent(@Payload UserEventDto userEventDto){
       log.info("Entering rabbitMQ broker and sending message to save User...");
        UserModel userModel = userEventDto.convertToUserModel();

        switch (ActionType.valueOf(userEventDto.getActionType())) {
            case CREATE, UPDATE -> userService.save(userModel);
            case DELETE -> userService.delete(userEventDto.getUserId());
        }
    }
}
