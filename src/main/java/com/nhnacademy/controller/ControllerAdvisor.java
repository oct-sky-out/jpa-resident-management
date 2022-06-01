package com.nhnacademy.controller;

import com.nhnacademy.exceptions.ExceptionTemplate;
import com.nhnacademy.exceptions.HasNotFamilyException;
import com.nhnacademy.exceptions.HouseholdNotFoundException;
import com.nhnacademy.exceptions.InvalidCertificationException;
import com.nhnacademy.exceptions.ResidentNotFoundExceprtion;
import com.nhnacademy.exceptions.ValidationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerAdvisor {
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ExceptionTemplate.class)
    public ExceptionTemplate notFoundException(ExceptionTemplate e){
        return e;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ValidationFailedException.class)
    public ExceptionTemplate clientErrorException(ValidationFailedException e){
        return new ExceptionTemplate(400, e.getMessage());
    }

    @ExceptionHandler({
        HouseholdNotFoundException.class,
        ResidentNotFoundExceprtion.class,
        InvalidCertificationException.class,
        HasNotFamilyException.class
    })
    public String viewErrorHandler(RuntimeException e, Model model){
        model.addAttribute("error", e.getMessage());
        return "error";
    }
}
