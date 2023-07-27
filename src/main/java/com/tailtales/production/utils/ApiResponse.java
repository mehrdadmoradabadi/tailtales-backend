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
}

