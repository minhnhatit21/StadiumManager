package com.minhnhat.Quanlysanbong.service;

import com.minhnhat.Quanlysanbong.model.ResponseDataModel;
import com.minhnhat.Quanlysanbong.models.*;
import com.minhnhat.Quanlysanbong.payload.request.BookingRequest;
import com.minhnhat.Quanlysanbong.payload.response.StadiumDetailsResponse;
import com.minhnhat.Quanlysanbong.repository.BookingConfirmRepository;
import com.minhnhat.Quanlysanbong.repository.StadiumPriceRepository;
import com.minhnhat.Quanlysanbong.repository.StadiumRepository;
import com.minhnhat.Quanlysanbong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StadiumService {

    private final StadiumRepository stadiumRepository;

    private final StadiumPriceRepository stadiumPriceRepository;

    private final BookingConfirmRepository bookingConfirmRepository;

    private final UserRepository userRepository;

    @Autowired
    public StadiumService(StadiumRepository stadiumRepository, StadiumPriceRepository stadiumPriceRepository
            , BookingConfirmRepository bookingConfirmRepository, UserRepository userRepository) {
        this.stadiumRepository = stadiumRepository;
        this.stadiumPriceRepository = stadiumPriceRepository;
        this.bookingConfirmRepository = bookingConfirmRepository;
        this.userRepository = userRepository;
    }

    public List<Stadium> getAll() {
       return stadiumRepository.findAllWithDetails();
    }

    public List<StadiumPrice> getAllStadiumPrice() {
        return stadiumPriceRepository.findAllDetails();
    }

    public List<StadiumDetailsResponse> getAllStadiumByCurrentDate() {
        List<StadiumDetailsResponse> newListStadium = new ArrayList<>();
        try {
            List<StadiumPrice> exitingStadium = stadiumPriceRepository.findAllDetails();

            // Get the current LocalDate
            LocalDate localDate = LocalDate.now();
            // Convert LocalDate to Date
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());


            for (StadiumPrice stadium : exitingStadium) {
                BookingConfirmation bookingConfirmation = bookingConfirmRepository.findBookingByDateBookingAndStadiumID(date, stadium.getId());
                StadiumDetailsResponse stadiumDetailsResponse = new StadiumDetailsResponse();
                stadiumDetailsResponse.setId(stadium.getId());
                stadiumDetailsResponse.setStadiumType(stadium.getStadiumType());
                stadiumDetailsResponse.setStadiumName(stadium.getStadium().getStadiumName());
                stadiumDetailsResponse.setStadiumTimeBlock(stadium.getTime());
                stadiumDetailsResponse.setStadiumPrice(stadium.getPrice());
                if(bookingConfirmation != null) {
                    stadiumDetailsResponse.setUserID(bookingConfirmation.getUser().getId());
                    stadiumDetailsResponse.setStadiumStatus(bookingConfirmation.getEStatus());
                } else {
                    stadiumDetailsResponse.setStadiumStatus(EStatus.NONE);
                }
                newListStadium.add(stadiumDetailsResponse);
            }
        } catch (Exception e) {

        }
        return newListStadium;
    }

    @Transactional
            (rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
    public ResponseDataModel updateStatusStadium(BookingRequest bookingRequest) {
        ResponseDataModel responseDataModel = new ResponseDataModel();
        String responseMsg = "";
        BookingConfirmation bookingConfirmation = new BookingConfirmation();
        // Get the current LocalDate
        LocalDate localDate = LocalDate.now();
        // Convert LocalDate to Date
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        try {
            StadiumPrice stadiumPrice = stadiumPriceRepository.findStadiumById(bookingRequest.getStadiumID());
            Optional<User> user = userRepository.findById(bookingRequest.getUserID());
            if(stadiumPrice == null) {
                responseMsg = "Can't find stadium";
            } else {
                BookingConfirmation exitingBookingConfirmation = bookingConfirmRepository.findBookingByDateBookingAndStadiumAndUserID(date, stadiumPrice.getId(), user.get().getId());
                if(exitingBookingConfirmation != null) {
                    responseMsg = "Exiting booking confirms";
                } else {
                    bookingConfirmation.setBookingDate(bookingRequest.getBookingDate());
                    bookingConfirmation.setStadiumPrice(stadiumPrice);
                    bookingConfirmation.setUser(user.get());
                    bookingConfirmation.setEStatus(EStatus.WAITING);
                    bookingConfirmRepository.save(bookingConfirmation);

                    responseMsg = "Booking successfully";
                    responseDataModel.setData(bookingConfirmation);
                }
            }
        } catch (Exception e) {
            responseMsg = "Error when updating status: \n" + e;
        }
        responseDataModel.setResponseMsg(responseMsg);
        return responseDataModel;
    }

    public ResponseDataModel getUserInformation(Long stadiumID) {
        ResponseDataModel responseDataModel = new ResponseDataModel();
        String responseMsg = "";
        try {
            List<BookingConfirmation> exitingBooking = bookingConfirmRepository.findBookingByStadiumID(stadiumID);
            responseDataModel.setData(exitingBooking);
        } catch (Exception e) {
            responseMsg = "Has Error:" + e;
        }
        responseDataModel.setResponseMsg(responseMsg);
        return responseDataModel;
    }
}
