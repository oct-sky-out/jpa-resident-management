package com.nhnacademy.controller;

import com.nhnacademy.dto.familyrelationship.ModifyFamilyRelationshipDto;
import com.nhnacademy.dto.familyrelationship.RegisterFamilyRelationshipDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.entity.FamilyRelationship;
import com.nhnacademy.exceptions.ValidationFailedException;
import com.nhnacademy.service.FamilyRelationshipService;
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
@RequestMapping("/residents/{serialNumber}/relationship")
public class FamilyRelationShipController {
    private final FamilyRelationshipService familyRelationshipService;

    public FamilyRelationShipController(FamilyRelationshipService familyRelationshipService) {
        this.familyRelationshipService = familyRelationshipService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SuccessDto<RegisterFamilyRelationshipDto> registerFamilyRelationship(@PathVariable("serialNumber") Long serialNumber,
                                                                                @Valid @RequestBody
                                                                                RegisterFamilyRelationshipDto familyRelationShipDto,
                                                                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }

        return familyRelationshipService.registerFamilyRelation(serialNumber, familyRelationShipDto);
    }

    @PutMapping("/{familySerialNumber}")
    public SuccessDto<FamilyRelationship> modifyFamilyRelationship(@PathVariable("serialNumber") Long serialNumber,
                                                                   @PathVariable("familySerialNumber") Long familySerialNumber,
                                                                   @Valid @RequestBody
                                                                              ModifyFamilyRelationshipDto familyRelationShipDto,
                                                                   BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }

        return familyRelationshipService.modifyFamilyRelation(serialNumber, familySerialNumber, familyRelationShipDto);
    }

    @DeleteMapping("/{familySerialNumber}")
    public SuccessDto<FamilyRelationship> breakUpFamilyRelationship(@PathVariable("serialNumber") Long serialNumber,
                                                                    @PathVariable("familySerialNumber") Long familySerialNumber){
        return familyRelationshipService.removeFamilyRelation(serialNumber, familySerialNumber);
    }
}
