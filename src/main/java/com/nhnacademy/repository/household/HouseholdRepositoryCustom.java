package com.nhnacademy.repository.household;

import com.nhnacademy.dto.household.HouseholdViewDto;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface HouseholdRepositoryCustom {
    Optional<HouseholdViewDto> findBy(Long householdSerial);
}
