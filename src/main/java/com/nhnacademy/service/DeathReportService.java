package com.nhnacademy.service;

import com.nhnacademy.dto.deathreport.ModifyDeathReportDto;
import com.nhnacademy.dto.deathreport.RegisterDeathReportDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.entity.BirthDeathReportResident;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.exceptions.ExceptionTemplate;
import com.nhnacademy.repository.birtdeathreportresident.BirthDeathReportResidentRepository;
import com.nhnacademy.repository.resident.ResidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeathReportService {
    private final BirthDeathReportResidentRepository deathReportResidentRepository;
    private final ResidentRepository residentRepository;


    public DeathReportService(BirthDeathReportResidentRepository deathReportResidentRepository,
                              ResidentRepository residentRepository) {
        this.deathReportResidentRepository = deathReportResidentRepository;
        this.residentRepository = residentRepository;
    }

    @Transactional
    public SuccessDto<RegisterDeathReportDto> registerDeathReport(Long serialNumber,
                                                                  RegisterDeathReportDto deathReportDto) {
        Resident resident = residentRepository.findById(serialNumber)
            .orElseThrow(() -> new ExceptionTemplate(404, "주민이 존재하지않습니다."));

        BirthDeathReportResident.Pk pk = new BirthDeathReportResident.Pk(
            deathReportDto.getBirthDeathTypeCode(),
            deathReportDto.getReportSerialNumber(),
            resident.getSerialNumber()
        );

        BirthDeathReportResident birthDeathReportResident = BirthDeathReportResident.builder()
            .pk(pk)
            .reportDate(deathReportDto.getReportDate())
            .deathReportQualificationsCode(deathReportDto.getDeathReportQualificationsCode())
            .email(deathReportDto.getEmail())
            .phoneNumber(deathReportDto.getPhoneNumber())
            .resident(resident)
            .build();

        deathReportResidentRepository.saveAndFlush(birthDeathReportResident);
        return new SuccessDto<>(deathReportDto);
    }

    @Transactional
    public SuccessDto<BirthDeathReportResident> modifyDeathReport(Long serialNumber,
                                                                  Long targetSerialNumber,
                                                                  ModifyDeathReportDto modifyDeathReportDto) {
        BirthDeathReportResident.Pk pk =
            new BirthDeathReportResident.Pk("사망", targetSerialNumber, serialNumber);
        BirthDeathReportResident deathReportResident =
            deathReportResidentRepository.findById(pk)
                .orElseThrow(() -> new ExceptionTemplate(404, "사망등록정보가 존재하지않습니다."));

        deathReportResident.modifyDeathReport(
            modifyDeathReportDto.getDeathReportQualificationsCode(),
            modifyDeathReportDto.getReportDate(),
            modifyDeathReportDto.getEmail(),
            modifyDeathReportDto.getPhoneNumber()
        );

        return new SuccessDto<>(
            deathReportResidentRepository.saveAndFlush(deathReportResident));
    }

    @Transactional
    public SuccessDto<BirthDeathReportResident> removeDeathReport(Long serialNumber,
                                                                  Long targetSerialNumber) {
        BirthDeathReportResident.Pk pk =
            new BirthDeathReportResident.Pk("사망", targetSerialNumber, serialNumber);
        BirthDeathReportResident deathReportResident =
            deathReportResidentRepository.findById(pk)
                .orElseThrow(() -> new ExceptionTemplate(404, "출생등록정보가 존재하지않습니다."));

        deathReportResidentRepository.deleteById(pk);

        return new SuccessDto<>(deathReportResident);
    }
}
