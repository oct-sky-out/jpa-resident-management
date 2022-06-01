package com.nhnacademy.controller;

import com.nhnacademy.dto.birthreport.ModifyBirthReportDto;
import com.nhnacademy.dto.birthreport.RegisterBirthReportDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.entity.BirthDeathReportResident;
import com.nhnacademy.exceptions.ValidationFailedException;
import com.nhnacademy.service.BirthReportService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("residents/{serialNumber}/birth")
public class BirthReportController {
    private final BirthReportService birthReportService;

    public BirthReportController(BirthReportService birthReportService) {
        this.birthReportService = birthReportService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessDto<RegisterBirthReportDto> registerBirthReport(@PathVariable("serialNumber") Long serialNumber,
                                                                  @Valid @RequestBody
                                                                  RegisterBirthReportDto registerBrithReportDto,
                                                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return birthReportService.registerBirthReport(serialNumber, registerBrithReportDto);
    }

    @PutMapping("/{targetSerialNumber}")
    public SuccessDto<BirthDeathReportResident> registerBirthReport(@PathVariable("serialNumber") Long serialNumber,
                                                                    @PathVariable("targetSerialNumber") Long targetSerialNumber,
                                                                    @Valid @RequestBody
                                                                    ModifyBirthReportDto modifyBirthReportDto,
                                                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return birthReportService.modifyBirthReport(serialNumber, targetSerialNumber, modifyBirthReportDto);
    }

    @DeleteMapping("/{targetSerialNumber}")
    public SuccessDto<BirthDeathReportResident> removeBirthReport(@PathVariable("serialNumber") Long serialNumber,
                                                                  @PathVariable("targetSerialNumber") Long targetSerialNumber){
        return birthReportService.removeBirthReport(serialNumber, targetSerialNumber);
    }
}
