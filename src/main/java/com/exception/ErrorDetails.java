package com.exception;

import lombok.Data;

@Data
public class ErrorDetails {

    private final String message;

    private final long timeStamp;

    public ErrorDetails(String message) {
        this.message = message;
        this.timeStamp =System.currentTimeMillis();
    }
}
