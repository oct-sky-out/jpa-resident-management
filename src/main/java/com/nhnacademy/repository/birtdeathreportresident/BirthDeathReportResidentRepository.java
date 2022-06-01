package com.nhnacademy.repository.birtdeathreportresident;

import com.nhnacademy.dto.deathreport.DeathIssueDto;
import com.nhnacademy.dto.birthreport.BirthIssueDto;
import com.nhnacademy.entity.BirthDeathReportResident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BirthDeathReportResidentRepository
    extends JpaRepository<BirthDeathReportResident, BirthDeathReportResident.Pk>,
    BirthDeathReportResidentRepositoryCustom {

    @Query("select b.reportDate as reportDate, " +
        "   b.resident.name as name," +
        "   b.resident.genderCode as genderCode," +
        "   b.resident.birthDate as birthDate," +
        "   b.resident.birthPlaceCode as placeCode," +
        "   b.resident.baseAddress as baseAddress, " +
        "   reporter.name as reporterName," +
        "   reporter.registrationNumber as reporterRegistrationNumber, " +
        "   b.birthReportQualificationsCode as qualificationCode, " +
        "   b.email as email, " +
        "   b.phoneNumber as phoneNumber " +
        "from BirthDeathReportResident b " +
        " inner join Resident reporter " +
        "   on reporter.serialNumber = b.pk.reportSerialNumber " +
        " where b.pk.birthDeathTypeCode ='출생'" +
        "   and b.pk.serialNumber =:serialNumber")
    BirthIssueDto findResidentBirthIssueBy(@Param("serialNumber") Long serialNumber);


    DeathIssueDto findResidentDeathIssueBy(Long serialNumber);
}
