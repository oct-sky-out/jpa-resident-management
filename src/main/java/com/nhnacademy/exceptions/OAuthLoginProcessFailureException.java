package com.nhnacademy.exceptions;

public class OAuthLoginProcessFailureException extends RuntimeException {
    public OAuthLoginProcessFailureException(String msg) {
        super(msg);
    }
}
