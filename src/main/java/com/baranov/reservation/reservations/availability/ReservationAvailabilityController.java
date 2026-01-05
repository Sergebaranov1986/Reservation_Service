package com.baranov.reservation.reservations.availability;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation/availability")
public class ReservationAvailabilityController {
    private ReservationAvailabilityService reservationAvailabilityService;
    public static final Logger log = LoggerFactory.getLogger(ReservationAvailabilityController.class);

    public ReservationAvailabilityController(ReservationAvailabilityService reservationAvailabilityService) {
        this.reservationAvailabilityService = reservationAvailabilityService;
    }

    @PostMapping("/check")
    public ResponseEntity<CheckAvailabilityResponse> checkAvailability(
            @Valid CheckAvailabilityRequest request) {
        log.info("Check availability method called^ request={}", request);
        boolean isAvailable = reservationAvailabilityService.isReservationAvailable(
                request.roomId(),
                request.startDate(),
                request.endDate()
        );
        var message = isAvailable ? "Room Available" : "Room Not available";
        var status = isAvailable ? AvailabilityStatus.AVAILABLE : AvailabilityStatus.RESERVED;
        return ResponseEntity
                .status(200)
                .body(new CheckAvailabilityResponse(message, status));
    }
}
