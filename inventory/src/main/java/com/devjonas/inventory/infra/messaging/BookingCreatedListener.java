package com.devjonas.inventory.infra.messaging;

import com.devjonas.inventory.application.usecase.MarkVehicleAsReserved;
import com.devjonas.inventory.domain.event.BookingCreatedEvent;
import com.devjonas.inventory.infra.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookingCreatedListener {

    @Autowired
    private MarkVehicleAsReserved markVehicleAsReserved;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handle(BookingCreatedEvent event) {
        log.info("Evento recebido: bookingId={}", event);
        markVehicleAsReserved.execute(event.vehicleId());
    }
}
