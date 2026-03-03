package com.devjonas.booking.domain.entities;

import com.devjonas.booking.application.dto.VehicleDTO;
import com.devjonas.booking.domain.enums.BookingStatus;
import com.devjonas.booking.domain.exception.BookingInvalidatePeriodException;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
public class Booking extends BaseEntity {

    @Column(name = "vehicle_id")
    private UUID vehicleId;

    @Column(name = "customer_id")
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;


    private Booking(UUID vehicleId, UUID customerId, BookingStatus status, LocalDateTime startDate, LocalDateTime endDate, BigDecimal totalAmount) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
    }

    protected Booking() {}

    public static Booking create(VehicleDTO vehicle, UUID customerId, LocalDateTime startDate, LocalDateTime endDate) {
        validateDates(startDate, endDate);

        BigDecimal totalAmount = calculateAmount(vehicle.dailyRate(), startDate, endDate);

        BookingStatus initialStatus = BookingStatus.PENDING;

        return new Booking(vehicle.id(), customerId, initialStatus, startDate, endDate, totalAmount);
    }

    private static void validateDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BookingInvalidatePeriodException();
        }

        if (startDate.isBefore(LocalDateTime.now())) {
            throw new BookingInvalidatePeriodException();
        }
    }

    private static BigDecimal calculateAmount(BigDecimal dailyRate, LocalDateTime startDate, LocalDateTime endDate) {
        Duration durationBetweenDates = Duration.between(startDate, endDate);
        long diffTotalInSeconds = durationBetweenDates.getSeconds();

        BigDecimal diffTotalInHours = BigDecimal.valueOf(diffTotalInSeconds)
                .divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);

        BigDecimal minimumHours = BigDecimal.valueOf(2);
        if (diffTotalInHours.compareTo(minimumHours) < 0) {
            throw new BookingInvalidatePeriodException();
        }

        BigDecimal hoursInDay = BigDecimal.valueOf(24);

        if (diffTotalInHours.compareTo(hoursInDay) < 0) {
            return dailyRate.multiply(diffTotalInHours).divide(hoursInDay, 2, RoundingMode.HALF_UP);
        } else {
            BigDecimal totalDays = diffTotalInHours.divide(hoursInDay, 0 , RoundingMode.UP);

            return dailyRate.multiply(totalDays);
        }

    }
}
