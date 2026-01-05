package com.baranov.reservation.reservations;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Modifying
    @Query("""

            Update ReservationEntity r
            Set r.status= :status
            Where r.id= :id
            """)
    void setStatus(
            @Param("id") Long id,
            @Param("status") ReservationStatus reservationStatus);

    @Query("""
            Select r.id from ReservationEntity r
            Where r.id = :roomId
            and :startDate < r.endDate 
            and r.startDate < :endDate
            and r.status = :status
                    """)
    List<Long> findConflictReservationIds(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") ReservationStatus status);

    @Query("""
            Select r from ReservationEntity r
            Where (:roomId IS NULL OR r.roomId = :roomId)
            and (:userId IS NULL OR r.userId = :userId)
            """)
List<ReservationEntity> searchAllByFilter(
        @Param("roomId") Long roomId,
        @Param("userId") Long userId,
        Pageable pageable


);
}

