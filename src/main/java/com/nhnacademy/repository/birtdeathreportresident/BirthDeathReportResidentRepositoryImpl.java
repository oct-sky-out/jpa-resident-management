package com.nhnacademy.repository.birtdeathreportresident;

import com.nhnacademy.dto.deathreport.DeathIssueDto;
import com.nhnacademy.entity.BirthDeathReportResident;
import com.nhnacademy.entity.QBirthDeathReportResident;
import com.nhnacademy.entity.QResident;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BirthDeathReportResidentRepositoryImpl extends QuerydslRepositorySupport
    implements  BirthDeathReportResidentRepositoryCustom {
    public BirthDeathReportResidentRepositoryImpl() {
        super(BirthDeathReportResident.class);
    }

    @Override
    public DeathIssueDto findResidentDeathIssueBy(Long serialNumber) {
        QBirthDeathReportResident birthDeathReportResident =
            QBirthDeathReportResident.birthDeathReportResident;
        QResident resident = QResident.resident;

        return from(birthDeathReportResident)
            .join(birthDeathReportResident.resident, resident)
            .join(resident)
            .on(birthDeathReportResident.pk.reportSerialNumber.eq(resident.serialNumber))
            .where(birthDeathReportResident.pk.birthDeathTypeCode.eq("사망")
                .and(birthDeathReportResident.pk.serialNumber.eq(serialNumber)))
            .select(
                Projections.constructor(DeathIssueDto.class,
                    birthDeathReportResident.reportDate,
                    birthDeathReportResident.resident.name,
                    birthDeathReportResident.resident.registrationNumber,
                    birthDeathReportResident.resident.deathDate,
                    birthDeathReportResident.resident.deathPlaceCode,
                    birthDeathReportResident.resident.deathPlaceAddress,
                    resident.name,
                    resident.registrationNumber,
                    birthDeathReportResident.deathReportQualificationsCode,
                    birthDeathReportResident.email,
                    birthDeathReportResident.phoneNumber
                    ))
            .fetchOne();
    }
}
