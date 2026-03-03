package com.devjonas.booking.infra.messaging;

import com.devjonas.booking.domain.event.BookingCancelledEvent;
import com.devjonas.booking.infra.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingCancelledEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(BookingCancelledEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.BOOKING_CANCELLED_ROUTING_KEY,
                event
        );
    }

}
