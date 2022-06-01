package com.nhnacademy.repository.householdcomposition;

import com.nhnacademy.dto.householdcomposition.HouseholdCompositionViewDto;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface HouseholdCompositionResidentRepositoryCustom {
    Optional<Long> findHouseholdSerialByResidentSerial(Long residentSerial);

    List<HouseholdCompositionViewDto> findHouseholdCompositionBy(Long householdSerial);
}
