package com.devjonas.indentity.infra.messaging;

import com.devjonas.indentity.domain.event.UserCreatedEvent;
import com.devjonas.indentity.infra.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(UserCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.USER_CREATED_ROUTING_KEY,
                event
        );
    }
}
