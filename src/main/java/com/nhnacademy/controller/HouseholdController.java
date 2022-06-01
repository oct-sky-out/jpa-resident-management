package com.nhnacademy.controller;

import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.dto.household.ModifyHouseholdDto;
import com.nhnacademy.dto.household.RegisterHouseHoldDto;
import com.nhnacademy.exceptions.ExceptionTemplate;
import com.nhnacademy.exceptions.ValidationFailedException;
import com.nhnacademy.service.HouseholdService;
import javax.validation.Valid;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/household")
public class HouseholdController {
    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @PostMapping
    public SuccessDto<RegisterHouseHoldDto> registerHousehold(@Valid @RequestBody
                                                                  RegisterHouseHoldDto registerHouseHoldDto,
                                                              BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        return householdService.registerHouseHold(registerHouseHoldDto);
    }

    @PutMapping("/{householdSerialNumber}")
    public SuccessDto<ModifyHouseholdDto> modifyHouseholdDtoSuccessDto(@PathVariable("householdSerialNumber") Long serialNumber,
                                                                       @Valid @RequestBody ModifyHouseholdDto modifyHouseholdDto,
                                                                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }

        return householdService.modifyHousehold(serialNumber, modifyHouseholdDto);
    }

    @DeleteMapping("/{householdSerialNumber}")
    public SuccessDto<String> removeHousehold(@PathVariable("householdSerialNumber") Long serialNumber){
        try{
            return householdService.removeHousehold(serialNumber);
        } catch (EmptyResultDataAccessException e){
            throw new ExceptionTemplate(404, "존재하는 세대정보가 없습니다.");
        }
    }
}
