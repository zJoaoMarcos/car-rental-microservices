package com.devjonas.booking.infra.messaging;

import com.devjonas.booking.domain.event.BookingCreatedEvent;
import com.devjonas.booking.infra.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingCreatedEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(BookingCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.BOOKING_CREATED_ROUTING_KEY,
                event
        );
    }
}
