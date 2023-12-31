package com.minhnhat.Quanlysanbong.payload.response;

import com.minhnhat.Quanlysanbong.models.EStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class StadiumDetailsResponse {
    private Long id;
    private String stadiumName;
    private String stadiumType;
    private String stadiumTimeBlock;
    private Long stadiumPrice;
    private Long userID;


    @Enumerated(EnumType.STRING)
    private EStatus stadiumStatus;

    public StadiumDetailsResponse(Long id, String stadiumName, String stadiumType, String stadiumTimeBlock, Long stadiumPrice) {
        this.id = id;
        this.stadiumName = stadiumName;
        this.stadiumType = stadiumType;
        this.stadiumTimeBlock = stadiumTimeBlock;
        this.stadiumPrice = stadiumPrice;
    }
}
