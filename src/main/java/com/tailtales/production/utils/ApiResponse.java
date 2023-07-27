package com.tailtales.production.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private String ResponseCode;
    private String message;
    private T result;

    public ApiResponse(String responseCode, String message, T result) {
        ResponseCode = responseCode;
        this.message = message;
        this.result = result;
    }
    public ApiResponse( T result) {
        ResponseCode = "200";
        this.message = "Successful";
        this.result = result;
    }
}

