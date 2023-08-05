package com.minhnhat.Quanlysanbong.service;

import com.minhnhat.Quanlysanbong.model.ResponseDataModel;
import com.minhnhat.Quanlysanbong.models.BookingConfirmation;
import com.minhnhat.Quanlysanbong.models.User;
import com.minhnhat.Quanlysanbong.payload.request.BookingRequest;
import com.minhnhat.Quanlysanbong.repository.BookingConfirmRepository;
import com.minhnhat.Quanlysanbong.repository.StadiumPriceRepository;
import com.minhnhat.Quanlysanbong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final BookingConfirmRepository bookingConfirmRepository;

    private final StadiumPriceRepository stadiumPriceRepository;

    @Autowired
    public UserService(UserRepository userRepository, BookingConfirmRepository bookingConfirmRepository,
                       StadiumPriceRepository stadiumPriceRepository) {
        this.userRepository = userRepository;
        this.bookingConfirmRepository = bookingConfirmRepository;
        this.stadiumPriceRepository = stadiumPriceRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserID(String username) {
        User exitingUser = userRepository.findByUsername(username);
        return exitingUser;
    }
}
