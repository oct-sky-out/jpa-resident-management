package com.nhnacademy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.config.RootConfig;
import com.nhnacademy.config.WebConfig;
import com.nhnacademy.dto.householdcomposition.HouseholdCompositionViewDto;
import com.nhnacademy.repository.householdcomposition.HouseholdCompositionResidentRepository;
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
class HouseholdCompositionResidentRepositoryTest {
    @Autowired
    HouseholdCompositionResidentRepository repository;

    @Test
    void findHouseholdSerialFromHouseholdComposition() {
        assertThat(repository.findHouseholdSerialByResidentSerial(5L).stream()
            .findFirst()
            .get()
        ).isEqualTo(1);
    }

    @Test
    void findHouseholdCompositionByTest() {
        HouseholdCompositionViewDto dto = repository.findHouseholdCompositionBy(1L).get(0);

        assertThat(dto.getName()).isEqualTo("남기준");
    }
}
