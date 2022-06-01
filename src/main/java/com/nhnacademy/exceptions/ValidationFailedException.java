package com.nhnacademy.exceptions;

import java.util.stream.Collectors;
import org.springframework.validation.BindingResult;

public class ValidationFailedException extends RuntimeException {
    private String message;

    public ValidationFailedException(BindingResult bindingResult) {
        this.message = bindingResult.getAllErrors()
            .stream()
            .map(err -> new StringBuilder().append("Object name=").append(err.getObjectName())
                .append(", Message=").append(err.getDefaultMessage())
                .append(", code=").append(err.getCode()))
            .collect(Collectors.joining(" | "));
    }

    @Override
    public String getMessage() {
        return message;
    }
}
