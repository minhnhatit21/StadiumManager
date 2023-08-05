package com.minhnhat.Quanlysanbong.service;

import com.minhnhat.Quanlysanbong.model.ResponseDataModel;
import com.minhnhat.Quanlysanbong.models.*;
import com.minhnhat.Quanlysanbong.payload.request.BookingRequest;
import com.minhnhat.Quanlysanbong.repository.BookingConfirmRepository;
import com.minhnhat.Quanlysanbong.repository.StadiumPriceRepository;
import com.minhnhat.Quanlysanbong.repository.StadiumRepository;
import com.minhnhat.Quanlysanbong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
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

    @Transactional
            (rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
    public ResponseDataModel updateStatusStadium(BookingRequest bookingRequest) {
        ResponseDataModel responseDataModel = new ResponseDataModel();
        String responseMsg = "";
        BookingConfirmation bookingConfirmation = new BookingConfirmation();
        try {
            StadiumPrice stadiumPrice = stadiumPriceRepository.findStadiumById(bookingRequest.getStadiumID());
            Optional<User> user = userRepository.findById(bookingRequest.getUserID());
            if(stadiumPrice != null) {
                /* Change Status Stadium */
                stadiumPrice.setStatus(EStatus.WAITING);
                stadiumPriceRepository.saveAndFlush(stadiumPrice);

                /* Add data bookingConfirmation */
                bookingConfirmation.setBookingDate(bookingRequest.getBookingDate());
                bookingConfirmation.setStadiumPrice(stadiumPrice);
                bookingConfirmation.setUser(user.get());
                bookingConfirmation.setEStatus(EStatus.WAITING);
                bookingConfirmRepository.save(bookingConfirmation);

                responseMsg = "Booking successfully";
                responseDataModel.setData(bookingConfirmation);
            } else {
                responseMsg = "Can't find this stadium by " + bookingRequest.getBookingDate();
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
