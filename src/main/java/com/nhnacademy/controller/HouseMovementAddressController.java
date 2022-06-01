package com.nhnacademy.controller;

import com.nhnacademy.dto.housemovementaddress.ModifyHouseholdMovementAddressDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.dto.housemovementaddress.RegisterHouseMovementAddressDto;
import com.nhnacademy.entity.HouseholdMovementAddress;
import com.nhnacademy.exceptions.ExceptionTemplate;
import com.nhnacademy.exceptions.ValidationFailedException;
import com.nhnacademy.service.HouseMovementAddressService;
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
@RequestMapping("/household/{householdSerialNumber}/movement")
public class HouseMovementAddressController {
    private final HouseMovementAddressService houseMovementAddressService;

    public HouseMovementAddressController(HouseMovementAddressService houseMovementAddressService) {
        this.houseMovementAddressService = houseMovementAddressService;
    }

    @PostMapping
    public SuccessDto<HouseholdMovementAddress> registerHouseMovementAddress(@PathVariable("householdSerialNumber") Long serialNumber,
                                                                             @Valid @RequestBody
                                                                             RegisterHouseMovementAddressDto houseMovementAddressDto,
                                                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }

        return houseMovementAddressService.registerHouseMovementAddress(serialNumber, houseMovementAddressDto);
    }

    @PutMapping("/{reportDate}")
    public SuccessDto<HouseholdMovementAddress> modifyHouseMovementAddress(@PathVariable("householdSerialNumber") Long serialNumber,
                                                                           @PathVariable("reportDate") String reportDate,
                                                                           @Valid @RequestBody
                                                                           ModifyHouseholdMovementAddressDto modifyHouseholdMovementAddressDto,
                                                                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }

        return houseMovementAddressService.modifyHouseMovementAddress(serialNumber, reportDate, modifyHouseholdMovementAddressDto);
    }

    @DeleteMapping("/{reportDate}")
    public SuccessDto<String> removeHouseMovementAddress(@PathVariable("householdSerialNumber") Long serialNumber,
                                                         @PathVariable("reportDate") String reportDate){
        try{
            return houseMovementAddressService.removeHouseMovementAddress(serialNumber, reportDate);
        } catch (EmptyResultDataAccessException e){
            throw new ExceptionTemplate(404, "존재하는 세대전입 정보가 없습니다.");
        }
    }
}
