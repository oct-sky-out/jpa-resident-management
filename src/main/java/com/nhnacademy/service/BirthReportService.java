package com.nhnacademy.service;

import com.nhnacademy.dto.birthreport.ModifyBirthReportDto;
import com.nhnacademy.dto.birthreport.RegisterBirthReportDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.entity.BirthDeathReportResident;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.exceptions.ExceptionTemplate;
import com.nhnacademy.repository.birtdeathreportresident.BirthDeathReportResidentRepository;
import com.nhnacademy.repository.resident.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BirthReportService {
    private final BirthDeathReportResidentRepository birthReportResidentRepository;
    private final ResidentRepository residentRepository;

    public BirthReportService(BirthDeathReportResidentRepository birthReportResidentRepository,
                              ResidentRepository residentRepository) {
        this.birthReportResidentRepository = birthReportResidentRepository;
        this.residentRepository = residentRepository;
    }

    @Transactional
    public SuccessDto<RegisterBirthReportDto> registerBirthReport(Long serialNumber,
                                                                  RegisterBirthReportDto registerBrithReportDto) {
        Resident resident = residentRepository.findById(serialNumber)
            .orElseThrow(() -> new ExceptionTemplate(404, "주민이 존재하지않습니다."));

        BirthDeathReportResident.Pk pk = new BirthDeathReportResident.Pk(
            registerBrithReportDto.getBirthDeathTypeCode(),
            registerBrithReportDto.getReportSerialNumber(),
            resident.getSerialNumber()
        );

        BirthDeathReportResident birthDeathReportResident = BirthDeathReportResident.builder()
            .pk(pk)
            .reportDate(registerBrithReportDto.getReportDate())
            .birthReportQualificationsCode(
                registerBrithReportDto.getBirthReportQualificationsCode())
            .email(registerBrithReportDto.getEmail())
            .phoneNumber(registerBrithReportDto.getPhoneNumber())
            .resident(resident)
            .build();

        birthReportResidentRepository.saveAndFlush(birthDeathReportResident);
        return new SuccessDto<>(registerBrithReportDto);
    }

    @Transactional
    public SuccessDto<BirthDeathReportResident> modifyBirthReport(Long serialNumber,
                                                                  Long targetSerialNumber,
                                                                  ModifyBirthReportDto modifyBirthReportDto) {
        BirthDeathReportResident.Pk pk =
            new BirthDeathReportResident.Pk("출생", targetSerialNumber, serialNumber);
        BirthDeathReportResident birthReportResident =
            birthReportResidentRepository.findById(pk)
                .orElseThrow(() -> new ExceptionTemplate(404, "출생등록정보가 존재하지않습니다."));

        birthReportResident.modifyBirthReport(
            modifyBirthReportDto.getBirthReportQualificationsCode(),
            modifyBirthReportDto.getReportDate(),
            modifyBirthReportDto.getEmail(),
            modifyBirthReportDto.getPhoneNumber());

        return new SuccessDto<>(birthReportResidentRepository.saveAndFlush(birthReportResident));
    }

    @Transactional
    public SuccessDto<BirthDeathReportResident> removeBirthReport(Long serialNumber,
                                                                  Long targetSerialNumber) {
        BirthDeathReportResident.Pk pk = new BirthDeathReportResident.Pk(
            "출생",
            targetSerialNumber,
            serialNumber
        );

        BirthDeathReportResident birthReportResident =
            birthReportResidentRepository.findById(pk)
                .orElseThrow(() -> new ExceptionTemplate(404, "출생등록정보가 존재하지않습니다."));

        birthReportResidentRepository.deleteById(pk);

        return new SuccessDto<>(birthReportResident);
    }
}
