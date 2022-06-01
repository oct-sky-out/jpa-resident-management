package com.nhnacademy.repository.household;

import com.nhnacademy.dto.household.HouseholdViewDto;
import com.nhnacademy.entity.Household;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface HouseholdRepository extends JpaRepository<Household, Long>, HouseholdRepositoryCustom {
    @Override
    Optional<HouseholdViewDto> findBy(Long householdSerial);

    @Transactional
    @Modifying
    @Query("delete from Household h where h.resident.serialNumber=:serialNumber")
    void deleteHouseholdBy(@Param("serialNumber") Long serialNumber);
}
