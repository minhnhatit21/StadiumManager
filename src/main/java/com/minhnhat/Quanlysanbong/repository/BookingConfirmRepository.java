package com.minhnhat.Quanlysanbong.repository;

import com.minhnhat.Quanlysanbong.models.BookingConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingConfirmRepository extends JpaRepository<BookingConfirmation, Long> {

    @Query("SELECT b FROM BookingConfirmation b WHERE b.stadiumPrice.id = :stadiumID")
    List<BookingConfirmation> findBookingByStadiumID(Long stadiumID);

    @Query(value = "SELECT b FROM BookingConfirmation b WHERE b.user.id = :userID")
    List<BookingConfirmation> findBookingByUserID(Long userID);

    @Query("SELECT b FROM BookingConfirmation b WHERE b.user.id = ?1 and b.stadiumPrice.id = ?2")
    BookingConfirmation findBookingByUserIDAndStadiumID(Long userID, Long stadiumID);

    @Query(
            value = "SELECT * FROM booking_confirmation b WHERE Date(b.booking_date) =  Date(:bookingDate) and b.stadium_price_id = :stadiumID",
            nativeQuery = true)
    List<BookingConfirmation> findBookingByDateBookingAndStadium(Date bookingDate, Long stadiumID);

    @Query(
            value = "SELECT * FROM booking_confirmation b WHERE Date(b.booking_date) =  Date(:bookingDate) and b.stadium_price_id = :stadiumID",
            nativeQuery = true)
    BookingConfirmation findBookingByDateBookingAndStadiumID(Date bookingDate, Long stadiumID);

    @Query(
            value = "SELECT * FROM booking_confirmation b WHERE Date(b.booking_date) =  Date(:bookingDate) and b.stadium_price_id = :stadiumID and b.user_id = :userID",
            nativeQuery = true)
    BookingConfirmation findBookingByDateBookingAndStadiumAndUserID(Date bookingDate, Long stadiumID, Long userID);
}
