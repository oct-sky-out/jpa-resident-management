package com.nhnacademy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.config.RootConfig;
import com.nhnacademy.config.WebConfig;
import com.nhnacademy.dto.familyrelationship.FamilyRelationshipCertificationDto;
import com.nhnacademy.entity.FamilyRelationship;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.repository.familyrelationship.FamilyRelationshipRepository;
import com.nhnacademy.repository.resident.ResidentRepository;
import java.util.List;
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
class FamilyRelationshipRepositoryTest {
    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    FamilyRelationshipRepository familyRelationshipRepository;

    @Test
    void register() {
        Resident baseResident = residentRepository.findById(8L).get();

        FamilyRelationship familyRelationship = FamilyRelationship.builder()
            .resident(baseResident)
            .pk(new FamilyRelationship.Pk(
                baseResident.getSerialNumber(),
                5L))
            .code("모")
            .build();

        FamilyRelationship relationship = familyRelationshipRepository.saveAndFlush(familyRelationship);
        assertThat(relationship.getCode()).isEqualTo("모");
        assertThat(relationship.getPk().getBaseSerialNumber()).isEqualTo(baseResident.getSerialNumber());
    }

    @Test
    void familyRelationshipPrintTest() {
        List<FamilyRelationshipCertificationDto> certificationDtos =
            familyRelationshipRepository.findFamilyRelationshipBySerialNumber(4L)
                .orElseThrow();

        assertThat(certificationDtos).hasSize(4);
        assertThat(certificationDtos.get(0).getName())
            .isEqualTo("남석환");
        assertThat(certificationDtos.get(1).getName())
            .isEqualTo("박한나");
        assertThat(certificationDtos.get(2).getName())
            .isEqualTo("이주은");
        assertThat(certificationDtos.get(3).getName())
            .isEqualTo("남기석");
    }
}
