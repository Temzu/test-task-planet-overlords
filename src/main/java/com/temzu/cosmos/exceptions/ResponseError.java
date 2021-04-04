package com.temzu.cosmos.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class ResponseError {
    private int status;
    private String message;
    private Date timestamp;

    public ResponseError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}
