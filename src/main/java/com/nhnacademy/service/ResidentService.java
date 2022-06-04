package com.nhnacademy.service;

import com.nhnacademy.dto.resident.ModifyResidentDto;
import com.nhnacademy.dto.resident.RegisterResidentDto;
import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.dto.resident.ResidentViewDto;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.exceptions.ExceptionTemplate;
import com.nhnacademy.exceptions.RemoveFailureException;
import com.nhnacademy.repository.resident.ResidentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResidentService {
    private final ResidentRepository residentRepository;
    public ResidentService(ResidentRepository residentRepository){
        this.residentRepository = residentRepository;
    }

    @Transactional
    public SuccessDto<Resident> registerResident(RegisterResidentDto residentDto) {
        Resident resident = Resident.builder()
            .serialNumber(residentDto.getSerialNumber())
            .name(residentDto.getName())
            .baseAddress(residentDto.getBaseAddress())
            .birthDate(residentDto.getBirthDate())
            .birthPlaceCode(residentDto.getBirthPlaceCode())
            .registrationNumber(residentDto.getRegistrationNumber())
            .genderCode(residentDto.getGenderCode())
            .build();

        Resident savedResident = residentRepository.saveAndFlush(resident);

        return new SuccessDto<>(savedResident);
    }

    @Transactional
    public SuccessDto<Resident> modifyResidentData(Long serialNumber,
                                                   ModifyResidentDto registerResidentDto) {
        Resident resident = residentRepository.findById(serialNumber)
            .orElseThrow(() -> new ExceptionTemplate(404, "주민을 찾을 수 없습니다."));

        resident.changeResidentInfo(registerResidentDto.getName(),
            registerResidentDto.getBaseAddress(),
            registerResidentDto.getBirthDate(),
            registerResidentDto.getBirthPlaceCode(),
            registerResidentDto.getRegistrationNumber(),
            registerResidentDto.getGenderCode());

        return new SuccessDto<>(residentRepository.saveAndFlush(resident));
    }

    public Page<ResidentViewDto> searchAllResidentToPageable(Pageable pageable) {
        return residentRepository.getAllResidentByPage(pageable);
    }

    @Transactional
    public void removeResident(Long serialNumber) {
        Long count = residentRepository.countFamilyNumberBySerialNumber(serialNumber);
        if(count > 1){
            throw new RemoveFailureException("남은 가족정보가 존재합니다.");
        }
        residentRepository.deleteById(serialNumber);
    }
}
