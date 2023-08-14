package com.minhnhat.Quanlysanbong.controller;

import com.minhnhat.Quanlysanbong.models.BookingConfirmation;
import com.minhnhat.Quanlysanbong.models.Stadium;
import com.minhnhat.Quanlysanbong.models.StadiumPrice;
import com.minhnhat.Quanlysanbong.payload.request.BookingRequest;
import com.minhnhat.Quanlysanbong.payload.response.StadiumDetailsResponse;
import com.minhnhat.Quanlysanbong.service.BookingConfirmService;
import com.minhnhat.Quanlysanbong.service.StadiumService;
import com.minhnhat.Quanlysanbong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/stadium")
public class StadiumController {

    @Autowired
    private StadiumService stadiumService;

    @Autowired
    private BookingConfirmService bookingConfirmService;

    @Autowired
    private UserService userService;

    private final ResourceLoader resourceLoader;

    public StadiumController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


    @GetMapping("/stadiums")
    @ResponseBody
    public ResponseEntity<List<Stadium>> getStadiums() {
        return ResponseEntity.ok(stadiumService.getAll());
    }

    @GetMapping("/stadiumDetails")
    @ResponseBody
    public ResponseEntity<List<StadiumPrice>> getStadiumDetails() {
        return ResponseEntity.ok(stadiumService.getAllStadiumPrice());
    }

    @PostMapping("/search")
    @ResponseBody
    public ResponseEntity<List<StadiumPrice>> searchStadium(@RequestParam String keyword) {
        return ResponseEntity.ok(stadiumService.searchByKeyword(keyword));
    }

    @GetMapping("/stadiumDetailsByCurrentDate")
    @ResponseBody
    public ResponseEntity<List<StadiumDetailsResponse>> getStadiumDetailsCurrentDate() {
        return ResponseEntity.ok(stadiumService.getAllStadiumByCurrentDate());
    }

    @GetMapping("/users/{stadiumID}")
    @ResponseBody
    public ResponseEntity<?> getUserInfo(@PathVariable Long stadiumID) {
        return ResponseEntity.ok(stadiumService.getUserInformation(stadiumID));
    }

    @PutMapping("/booking/")
    @ResponseBody
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> bookingStadium(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(stadiumService.updateStatusStadium(bookingRequest));
    }

    @GetMapping("/images/{imageName}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) {
        try {
            Resource resource = resourceLoader.getResource("classpath:/static/images/" + imageName);
            InputStream imageInputStream = resource.getInputStream();
            byte[] imageData = imageInputStream.readAllBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type based on image type

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/bookingInfo")
    @ResponseBody
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getBookingInfo(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingConfirmService.findBookingDate(bookingRequest));
    }

    @PutMapping("/bookingConfirmsForUser")
    @ResponseBody
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getBookingInfoWithUser(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingConfirmService.confirmsBookingForUser(bookingRequest));
    }

}
