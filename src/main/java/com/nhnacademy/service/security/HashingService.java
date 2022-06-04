package com.nhnacademy.service.security;

import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.repository.resident.ResidentRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HashingService {
    private final ResidentRepository residentRepository;
    private final PasswordEncoder passwordEncoder;

    public HashingService(ResidentRepository residentRepository, PasswordEncoder passwordEncoder) {
        this.residentRepository = residentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public SuccessDto<List<Resident>> hashResidentsPassword(){
        List<Resident> residents = residentRepository.findAll();
        residents.stream()
            .forEach(resident -> {
                Long serialNumber = resident.getSerialNumber();
                String hashedPassword = passwordEncoder.encode(resident.getPassword());
                log.debug(hashedPassword);
                residentRepository.modifyResidentPassword(serialNumber, hashedPassword);
            });

        return new SuccessDto<>(residentRepository.findAll());
    }
}
