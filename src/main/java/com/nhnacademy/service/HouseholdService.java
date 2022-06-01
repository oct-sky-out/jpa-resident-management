package com.nhnacademy.service;

import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.dto.household.ModifyHouseholdDto;
import com.nhnacademy.dto.household.RegisterHouseHoldDto;
import com.nhnacademy.entity.Household;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.exceptions.ExceptionTemplate;
import com.nhnacademy.repository.household.HouseholdRepository;
import com.nhnacademy.repository.resident.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HouseholdService {
    private final HouseholdRepository householdRepository;
    private final ResidentRepository residentRepository;

    public HouseholdService(HouseholdRepository householdRepository,
                            ResidentRepository residentRepository) {
        this.householdRepository = householdRepository;
        this.residentRepository = residentRepository;
    }

    @Transactional
    public SuccessDto<RegisterHouseHoldDto> registerHouseHold(RegisterHouseHoldDto houseHoldDto) {
        Resident resident = residentRepository.findById(houseHoldDto.getResidentSerialNumber())
            .orElseThrow(() -> new ExceptionTemplate(404, "존재하지 않는 세대주 주민일련번호입니다."));

        Household household = Household.builder()
            .resident(resident)
            .serialNumber(houseHoldDto.getHouseholdSerialNumber())
            .compositionDate(houseHoldDto.getCompositionDate())
            .compositionReasonCode(houseHoldDto.getCompositionReasonCode())
            .currentHouseMovementAddress(houseHoldDto.getCurrentHouseMovementAddress())
            .build();

        householdRepository.saveAndFlush(household);
        return new SuccessDto<>(houseHoldDto);
    }

    @Transactional
    public SuccessDto<ModifyHouseholdDto> modifyHousehold(Long serialNumber,
                                                          ModifyHouseholdDto modifyHouseholdDto) {
        Household household = householdRepository.findById(serialNumber)
            .orElseThrow(() -> new ExceptionTemplate(404, "존재하지 않는 세대주 주민일련번호입니다."));

        household.changeHouseholdInfo(
            modifyHouseholdDto.getCompositionDate(),
            modifyHouseholdDto.getCompositionReasonCode(),
            modifyHouseholdDto.getCurrentHouseMovementAddress()
        );

        householdRepository.deleteById(serialNumber);
        householdRepository.saveAndFlush(household);

        return new SuccessDto<>(modifyHouseholdDto);
    }

    @Transactional
    public SuccessDto<String> removeHousehold(Long serialNumber) {
        householdRepository.deleteById(serialNumber);
        return new SuccessDto<>("새대정보 삭제왼료");
    }
}
