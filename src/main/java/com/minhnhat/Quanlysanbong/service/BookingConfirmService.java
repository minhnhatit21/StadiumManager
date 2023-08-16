package com.minhnhat.Quanlysanbong.service;

import com.minhnhat.Quanlysanbong.model.ResponseDataModel;
import com.minhnhat.Quanlysanbong.models.BookingConfirmation;
import com.minhnhat.Quanlysanbong.models.EStatus;
import com.minhnhat.Quanlysanbong.payload.request.BookingRequest;
import com.minhnhat.Quanlysanbong.repository.BookingConfirmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@Service
public class BookingConfirmService {


    private final BookingConfirmRepository bookingConfirmRepository;

    @Autowired
    public BookingConfirmService(BookingConfirmRepository bookingConfirmRepository) {
        this.bookingConfirmRepository = bookingConfirmRepository;
    }

    public ResponseDataModel findBookingDate(BookingRequest bookingRequest) {
        ResponseDataModel responseDataModel = new ResponseDataModel();
        String responseMsg = "";
        try {
            List<BookingConfirmation> exitingBooking =
                    bookingConfirmRepository.findBookingByDateBookingAndStadium(bookingRequest.getBookingDate(), bookingRequest.getStadiumID());
            if(exitingBooking != null) {
                responseMsg = "Get Data Successfully";
                responseDataModel.setData(exitingBooking);
            } else {
                responseMsg = "Not Exiting";
                responseDataModel.setData(null);
            }
        } catch (Exception e) {
            responseMsg = "Has Error:" + e;
        }
        responseDataModel.setResponseMsg(responseMsg);
        return responseDataModel;

    }

    public ResponseDataModel findBookingDateByUser(Long userID) {
        ResponseDataModel responseDataModel = new ResponseDataModel();
        String responseMsg = "";
        try {
            List<BookingConfirmation> exitingBooking =
                   bookingConfirmRepository.findBookingByUserID(userID);
            if(exitingBooking != null) {
                responseMsg = "Get Data Successfully";
                responseDataModel.setData(exitingBooking);
            } else {
                responseMsg = "Not Exiting";
                responseDataModel.setData(null);
            }
        } catch (Exception e) {
            responseMsg = "Has Error:" + e;
        }
        responseDataModel.setResponseMsg(responseMsg);
        return responseDataModel;

    }

    @Transactional
            (rollbackFor = Exception.class, noRollbackFor = EntityNotFoundException.class)
    public ResponseDataModel confirmsBookingForUser(BookingRequest bookingRequest) {
        ResponseDataModel responseDataModel = new ResponseDataModel();
        String responseMsg = "";
        try {
            BookingConfirmation exitingBooking =
                    bookingConfirmRepository.findBookingByDateBookingAndStadiumAndUserID(bookingRequest.getBookingDate(), bookingRequest.getStadiumID(), bookingRequest.getUserID());
            if(exitingBooking != null) {
                responseMsg = "Get Data Successfully";
                exitingBooking.setEStatus(EStatus.CONFIRM);
                bookingConfirmRepository.save(exitingBooking);
                responseDataModel.setData(exitingBooking);
            } else {
                responseMsg = "Not Exiting";
                responseDataModel.setData(null);
            }
        } catch (Exception e) {
            responseMsg = "Has Error:" + e;
        }
        responseDataModel.setResponseMsg(responseMsg);
        return responseDataModel;
    }
}
