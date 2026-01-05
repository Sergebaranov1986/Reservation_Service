package com.baranov.reservation.reservations.availability;

import com.baranov.reservation.reservations.ReservationRepository;
import com.baranov.reservation.reservations.ReservationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationAvailabilityService {

   private final  ReservationRepository reservationRepository;

   public static final Logger log= LoggerFactory.getLogger(ReservationAvailabilityService.class);

    public ReservationAvailabilityService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
    public boolean isReservationAvailable(
            Long roomId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (!endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("End date should be after start date");
        }
        List<Long> conflictingIds = reservationRepository.findConflictReservationIds(roomId, startDate, endDate, ReservationStatus.APPROVED);
        if (conflictingIds.isEmpty()) {
            return true;
        }
        log.info("Conflict with: ids={} ", conflictingIds);
        return false;
    }
}
