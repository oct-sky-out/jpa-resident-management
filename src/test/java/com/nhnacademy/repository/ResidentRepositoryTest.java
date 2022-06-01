package com.nhnacademy.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.config.RootConfig;
import com.nhnacademy.config.WebConfig;
import com.nhnacademy.dto.resident.ResidentViewDto;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.repository.resident.ResidentRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
    @ContextConfiguration(classes = RootConfig.class),
    @ContextConfiguration(classes = WebConfig.class)
})
class ResidentRepositoryTest {

    @Autowired
    ResidentRepository residentRepository;

    @Test
    void residentInsertTest() {
        Resident resident = Resident.builder()
            .serialNumber(1234L)
            .name("hello")
            .baseAddress("경남 김해시")
            .birthDate(LocalDateTime.now())
            .birthPlaceCode("경남")
            .registrationNumber("12345")
            .genderCode("남")
            .build();

        residentRepository.saveAndFlush(resident);

        residentRepository.findById(1234L).ifPresent(resident1 ->
            assertThat(resident1.getSerialNumber()).isEqualTo(resident.getSerialNumber())
        );
    }

    @Test
    void residentFindAllPageTest() {
        Pageable pageable = PageRequest.of(0, 10);
        List<ResidentViewDto> result = residentRepository.getAllResidentByPage(pageable)
            .getContent();

        assertThat(result.get(6).getBirthIssue()).isTrue();
        assertThat(result.get(0).getDeathIssue()).isTrue();
        assertThat(result.get(0).getBirthIssue()).isFalse();
    }

    @Test
    @Transactional
    void removeOrphanEntitiesTest() {
        residentRepository.deleteById(8L);
    }
}

