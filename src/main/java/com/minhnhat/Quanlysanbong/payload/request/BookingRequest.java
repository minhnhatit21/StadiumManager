package com.minhnhat.Quanlysanbong.payload.request;

import lombok.Data;

import java.util.Date;

@Data
public class BookingRequest {
    Date bookingDate;
    Long stadiumID;
    Long userID;
}
