package com.nhnacademy.controller;

import com.nhnacademy.dto.deathreport.ModifyDeathReportDto;
import com.nhnacademy.dto.deathreport.RegisterDeathReportDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.entity.BirthDeathReportResident;
import com.nhnacademy.exceptions.ValidationFailedException;
import com.nhnacademy.service.DeathReportService;
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
@RequestMapping("/residents/{serialNumber}/death")
public class DeathReportController {
    private final DeathReportService deathReportService;

    public DeathReportController(DeathReportService deathReportService) {
        this.deathReportService = deathReportService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessDto<RegisterDeathReportDto> registerBirthReport(@PathVariable("serialNumber") Long serialNumber,
                                                                  @Valid @RequestBody
                                                                    RegisterDeathReportDto registerDeathReportDto,
                                                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return deathReportService.registerDeathReport(serialNumber, registerDeathReportDto);
    }

    @PutMapping("/{targetSerialNumber}")
    public SuccessDto<BirthDeathReportResident> registerBirthReport(@PathVariable("serialNumber") Long serialNumber,
                                                                    @PathVariable("targetSerialNumber") Long targetSerialNumber,
                                                                    @Valid @RequestBody
                                                                        ModifyDeathReportDto modifyBirthReportDto,
                                                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return deathReportService.modifyDeathReport(serialNumber, targetSerialNumber, modifyBirthReportDto);
    }

    @DeleteMapping("/{targetSerialNumber}")
    public SuccessDto<BirthDeathReportResident> removeBirthReport(@PathVariable("serialNumber") Long serialNumber,
                                                                  @PathVariable("targetSerialNumber") Long targetSerialNumber){
        return deathReportService.removeDeathReport(serialNumber, targetSerialNumber);
    }

}
