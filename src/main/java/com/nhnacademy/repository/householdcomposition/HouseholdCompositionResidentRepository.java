package com.nhnacademy.repository.householdcomposition;

import com.nhnacademy.dto.householdcomposition.HouseholdCompositionViewDto;
import com.nhnacademy.entity.HouseholdCompositionResident;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface HouseholdCompositionResidentRepository extends JpaRepository<HouseholdCompositionResident, HouseholdCompositionResident.Pk>,
    HouseholdCompositionResidentRepositoryCustom{
    Optional<Long> findHouseholdSerialByResidentSerial(@Param("residentSerial") Long residentSerial);

    @Override
    List<HouseholdCompositionViewDto> findHouseholdCompositionBy(Long householdSerial);
}
