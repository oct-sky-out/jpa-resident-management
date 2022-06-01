package com.nhnacademy.repository.familyrelationship;

import com.nhnacademy.dto.birthreport.BirthResidentParentDto;
import com.nhnacademy.dto.familyrelationship.FamilyRelationshipCertificationDto;
import com.nhnacademy.entity.FamilyRelationship;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FamilyRelationshipRepository extends JpaRepository<FamilyRelationship, FamilyRelationship.Pk>,
    FamilyRelationshipRepositoryCustom {
    @Query(value = "select f.code as relationshipCode, " +
        "                   r.name as name, " +
        "                   r.birthDate as birthDate," +
        "                   r.registrationNumber as registrationNumber," +
        "                   r.genderCode as genderCode" +
        "    from FamilyRelationship f" +
        "    inner join Resident r" +
        "            on r.serialNumber = f.pk.familySerialNumber" +
        "    where f.pk.baseSerialNumber = :serialNumber" +
        "   order by r.birthDate")
    Optional<List<FamilyRelationshipCertificationDto>> findFamilyRelationshipBySerialNumber(@Param("serialNumber") Long serialNumber);

    @Override
    List<BirthResidentParentDto> findResidentParents(Long serialNumber);
}
