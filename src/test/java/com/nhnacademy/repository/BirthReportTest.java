package com.nhnacademy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.config.RootConfig;
import com.nhnacademy.config.WebConfig;
import com.nhnacademy.entity.BirthDeathReportResident;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.repository.birtdeathreportresident.BirthDeathReportResidentRepository;
import com.nhnacademy.repository.resident.ResidentRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
class BirthReportTest {
    @Autowired
    BirthDeathReportResidentRepository birthDeathReportResidentRepository;

    @Autowired
    ResidentRepository residentRepository;

    @Test
    void register() {
        Resident resident = residentRepository.findById(8L).get();

        BirthDeathReportResident.Pk pk = new BirthDeathReportResident.Pk(
            "출생",
            6L,
            resident.getSerialNumber()
        );
        BirthDeathReportResident birthDeathReportResident = BirthDeathReportResident.builder()
            .pk(pk)
            .reportDate(LocalDate.now())
            .birthReportQualificationsCode("모")
            .email(null)
            .phoneNumber("010-2111-2222")
            .resident(resident)
            .build();

        BirthDeathReportResident savedBirthDeathReportResident =
            birthDeathReportResidentRepository.saveAndFlush(birthDeathReportResident);

        assertThat(savedBirthDeathReportResident.getPk().getReportSerialNumber()).isEqualTo(6);
        assertThat(savedBirthDeathReportResident.getPk().getSerialNumber()).isEqualTo(8);
    }
}
