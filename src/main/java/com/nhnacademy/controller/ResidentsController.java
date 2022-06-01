package com.nhnacademy.controller;

import com.nhnacademy.dto.resident.ModifyResidentDto;
import com.nhnacademy.dto.resident.RegisterResidentDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.exceptions.ValidationFailedException;
import com.nhnacademy.service.ResidentService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/residents", produces = "application/json;")
public class ResidentsController {
    private final ResidentService residentService;

    public ResidentsController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SuccessDto<Resident> registerNewResident(@Valid @RequestBody RegisterResidentDto resident,
                                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return residentService.registerResident(resident);
    }

    @PutMapping("/{serialNumber}")
    public SuccessDto<Resident> residentInformationModify(@PathVariable("serialNumber") Long serialNumber,
                                                          @Valid @RequestBody ModifyResidentDto registerResidentDto,
                                                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return residentService.modifyResidentData(serialNumber, registerResidentDto);
    }
}
