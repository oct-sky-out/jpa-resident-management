package com.nhnacademy.service;

import com.nhnacademy.dto.housemovementaddress.ModifyHouseholdMovementAddressDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.dto.housemovementaddress.RegisterHouseMovementAddressDto;
import com.nhnacademy.entity.Household;
import com.nhnacademy.entity.HouseholdMovementAddress;
import com.nhnacademy.exceptions.ExceptionTemplate;
import com.nhnacademy.repository.householdmovement.HouseholdMovementAddressRepository;
import com.nhnacademy.repository.household.HouseholdRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HouseMovementAddressService {
    private final HouseholdMovementAddressRepository householdMovementAddressRepository;
    private final HouseholdRepository householdRepository;

    public HouseMovementAddressService(
        HouseholdMovementAddressRepository householdMovementAddressRepository,
        HouseholdRepository householdRepository) {
        this.householdMovementAddressRepository = householdMovementAddressRepository;
        this.householdRepository = householdRepository;
    }

    @Transactional
    public SuccessDto<HouseholdMovementAddress> registerHouseMovementAddress(Long serialNumber,
                                                                             RegisterHouseMovementAddressDto houseMovementAddressDto) {
        Household household = householdRepository.findById(serialNumber)
            .orElseThrow(() -> new ExceptionTemplate(404, "세대 정보가 존재하지않습니다."));

        HouseholdMovementAddress.Pk pk = new HouseholdMovementAddress.Pk(
            houseMovementAddressDto.getLocalDate(),
            household.getSerialNumber()
        );

        HouseholdMovementAddress householdMovementAddress = HouseholdMovementAddress.builder()
            .household(household)
            .pk(pk)
            .address(houseMovementAddressDto.getAddress())
            .lastAddress(houseMovementAddressDto.getLastAddress())
            .build();


        return new SuccessDto<>(
            householdMovementAddressRepository.saveAndFlush(householdMovementAddress)
        );
    }

    @Transactional
    public SuccessDto<HouseholdMovementAddress> modifyHouseMovementAddress(Long serialNumber,
                                                                           String reportDate,
                                                                           ModifyHouseholdMovementAddressDto houseMovementAddressDto) {
        LocalDate localDateReportDate = parseDateTime(reportDate);
        HouseholdMovementAddress.Pk pk =
            new HouseholdMovementAddress.Pk(localDateReportDate, serialNumber);
        HouseholdMovementAddress householdMovementAddress =
            householdMovementAddressRepository.findById(pk)
                .orElseThrow(() -> new ExceptionTemplate(404, "세대 정보가 존재하지않습니다."));


        householdMovementAddress.changeAddressInfo(houseMovementAddressDto.getAddress(),
            houseMovementAddressDto.getLastAddress());

        return new SuccessDto<>(
            householdMovementAddressRepository.saveAndFlush(householdMovementAddress)
        );
    }


    @Transactional
    public SuccessDto<String> removeHouseMovementAddress(Long serialNumber, String reportDate) {
        LocalDate localDateReportDate = parseDateTime(reportDate);
        HouseholdMovementAddress.Pk pk = new HouseholdMovementAddress.Pk(
            localDateReportDate,
            serialNumber
        );

        householdMovementAddressRepository.deleteById(pk);
        return new SuccessDto<>("삭제완료");
    }

    private LocalDate parseDateTime(String reportDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(reportDate, dateTimeFormatter);
    }
}
