package com.nhnacademy.repository.resident;

import com.nhnacademy.dto.resident.ResidentUserDetails;
import com.nhnacademy.dto.resident.ResidentViewDto;
import com.nhnacademy.entity.Resident;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ResidentRepository extends JpaRepository<Resident, Long>, ResidentRepositoryCustom{
    @Override
    Page<ResidentViewDto> getAllResidentByPage(Pageable pageable);

    @Query("select count(h1.serialNumber) from Household h1 " +
        "    inner join h1.resident r " +
        "    left outer join HouseholdCompositionResident h2 " +
        "        on h1.serialNumber = h2.pk.householdSerialNumber " +
        "    where r.serialNumber = :serialNumber")
    Long countFamilyNumberBySerialNumber(@Param("serialNumber") Long serialNumber);

    @Transactional
    @Modifying
    @Query("update Resident r set r.password=:password where r.serialNumber=:serialNumber")
    void modifyResidentPassword(@Param("serialNumber") Long serialNumber, @Param("password")String password);

    Page<ResidentViewDto> getResidentAndMyHouseholders(Pageable pageable, String userId);

    Optional<ResidentUserDetails> findResidentByEmail(String email);
}
