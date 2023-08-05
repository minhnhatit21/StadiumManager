package com.minhnhat.Quanlysanbong.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class BookingConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stadium_price_id")
    private StadiumPrice stadiumPrice;

    @NotNull
    private Date bookingDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EStatus eStatus;
}
