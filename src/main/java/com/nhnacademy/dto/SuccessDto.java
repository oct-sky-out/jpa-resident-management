package com.nhnacademy.dto;

import lombok.Data;

@Data
public class SuccessDto <T>{
    private final String status = "OK";
    private final T result;

    public SuccessDto(T result) {
        this.result = result;
    }
}
