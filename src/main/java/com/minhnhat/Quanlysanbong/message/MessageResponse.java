package com.minhnhat.Quanlysanbong.message;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
