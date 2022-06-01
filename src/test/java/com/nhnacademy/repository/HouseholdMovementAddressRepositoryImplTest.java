package com.nhnacademy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.config.RootConfig;
import com.nhnacademy.config.WebConfig;
import com.nhnacademy.repository.householdmovement.HouseholdMovementAddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@WebAppConfiguration
@ContextHierarchy({
    @ContextConfiguration(classes = RootConfig.class),
    @ContextConfiguration(classes = WebConfig.class)
})
class HouseholdMovementAddressRepositoryImplTest {
    @Autowired
    HouseholdMovementAddressRepository householdMovementAddressRepository;

    @Test
    void findHouseMovementAddressesBy() {
        assertThat(householdMovementAddressRepository.findHouseMovementAddressesBy(1L)
            .get(0)
            .getAddress())
            .isEqualTo("경기도 성남시 분당구 대왕판교로 645번길");
    }
}
