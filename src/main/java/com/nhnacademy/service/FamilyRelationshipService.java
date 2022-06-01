package com.nhnacademy.service;

import com.nhnacademy.dto.familyrelationship.ModifyFamilyRelationshipDto;
import com.nhnacademy.dto.familyrelationship.RegisterFamilyRelationshipDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.entity.FamilyRelationship;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.exceptions.ExceptionTemplate;
import com.nhnacademy.repository.familyrelationship.FamilyRelationshipRepository;
import com.nhnacademy.repository.resident.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FamilyRelationshipService {
    private final ResidentRepository residentRepository;
    private final FamilyRelationshipRepository familyRelationshipRepository;

    public FamilyRelationshipService(ResidentRepository residentRepository,
                                     FamilyRelationshipRepository familyRelationshipRepository) {
        this.residentRepository = residentRepository;
        this.familyRelationshipRepository = familyRelationshipRepository;
    }

    @Transactional
    public SuccessDto<RegisterFamilyRelationshipDto> registerFamilyRelation(Long baseSerialNumber,
                                                                            RegisterFamilyRelationshipDto familyRelationShipDto) {
        Resident baseResident = residentRepository.findById(baseSerialNumber)
            .orElseThrow(() -> new ExceptionTemplate(404, "기준주민대상이 존재하지않습니다."));

        FamilyRelationship familyRelationship = FamilyRelationship.builder()
            .resident(baseResident)
            .pk(new FamilyRelationship.Pk(
                baseResident.getSerialNumber(),
                familyRelationShipDto.getFamilySerialNumber()))
            .code(parseRelationshipCode(familyRelationShipDto.getRelationShip()))
            .build();

        familyRelationshipRepository.saveAndFlush(familyRelationship);

        return new SuccessDto<>(familyRelationShipDto);
    }

    @Transactional
    public SuccessDto<FamilyRelationship> modifyFamilyRelation(Long baseSerialNumber,
                                                               Long familySerialNumber,
                                                               ModifyFamilyRelationshipDto familyRelationShipDto) {
        FamilyRelationship.Pk pk = new FamilyRelationship.Pk(baseSerialNumber, familySerialNumber);
        FamilyRelationship familyRelationship = familyRelationshipRepository.findById(pk)
            .orElseThrow(() -> new ExceptionTemplate(404, "가족관계 정보를 찾을 수 없습니다."));

        familyRelationship.changeCode(
            parseRelationshipCode(familyRelationShipDto.getRelationShip()));

        FamilyRelationship savedFamilyRelationship =
            familyRelationshipRepository.saveAndFlush(familyRelationship);

        return new SuccessDto<>(savedFamilyRelationship);
    }

    private String parseRelationshipCode(String relationShip) {
        switch (relationShip) {
            case "father":
                return "부";
            case "mother":
                return "모";
            case "spouse":
                return "배우자";
            case "child":
                return "자녀";
            default:
                throw new ExceptionTemplate(400, "올바른 관계명이 아닙니다.");
        }
    }

    @Transactional
    public SuccessDto<FamilyRelationship> removeFamilyRelation(Long baseSerialNumber,
                                                               Long familySerialNumber) {
        FamilyRelationship.Pk pk = new FamilyRelationship.Pk(baseSerialNumber, familySerialNumber);
        FamilyRelationship familyRelationship = familyRelationshipRepository.findById(pk)
            .orElseThrow(() -> new ExceptionTemplate(404, "가족관계 정보를 찾을 수 없습니다."));

        familyRelationshipRepository.delete(familyRelationship);
        return new SuccessDto<>(familyRelationship);
    }
}
