package com.minhnhat.Quanlysanbong.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Table(name = "stadium_price")
@Entity
@Data
public class StadiumPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_price_id")
    private Long id;

    @NotBlank
    private String time;

    @NotNull
    private Long price;

    @NotBlank
    private String stadiumType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EStatus status;

    @ManyToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @OneToMany(mappedBy = "stadiumPrice", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookingConfirmation> bookingConfirmation;


}
