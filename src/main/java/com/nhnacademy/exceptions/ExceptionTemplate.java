package com.nhnacademy.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionTemplate extends RuntimeException {
    private final int httpStatus;
    private final String reason;
}
