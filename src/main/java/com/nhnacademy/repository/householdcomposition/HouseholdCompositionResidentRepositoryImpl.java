package com.nhnacademy.repository.householdcomposition;

import com.nhnacademy.dto.householdcomposition.HouseholdCompositionViewDto;
import com.nhnacademy.entity.HouseholdCompositionResident;
import com.nhnacademy.entity.QHouseholdCompositionResident;
import com.nhnacademy.entity.QResident;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class HouseholdCompositionResidentRepositoryImpl extends QuerydslRepositorySupport
        implements HouseholdCompositionResidentRepositoryCustom {
    public HouseholdCompositionResidentRepositoryImpl() {
        super(HouseholdCompositionResident.class);
    }

    @Override
    public Optional<Long> findHouseholdSerialByResidentSerial(Long residentSerial) {
        QHouseholdCompositionResident householdCompositionResident =
            QHouseholdCompositionResident.householdCompositionResident;
        QResident resident = QResident.resident;

        Long result = from(householdCompositionResident)
            .join(householdCompositionResident.resident, resident)
            .where(resident.serialNumber.eq(residentSerial))
            .orderBy(householdCompositionResident.reportDate.desc())
            .select(householdCompositionResident.pk.householdSerialNumber)
            .fetchOne();

        return Optional.ofNullable(result);

    }

    @Override
    public List<HouseholdCompositionViewDto> findHouseholdCompositionBy(Long householdSerial) {
        QHouseholdCompositionResident householdCompositionResident =
            QHouseholdCompositionResident.householdCompositionResident;
        QResident resident = QResident.resident;

        return from(householdCompositionResident)
            .join(householdCompositionResident.resident, resident)
            .where(householdCompositionResident.pk.householdSerialNumber.eq(householdSerial))
            .select(Projections.constructor(HouseholdCompositionViewDto.class,
                    resident.name,
                    resident.registrationNumber,
                    householdCompositionResident.code,
                    householdCompositionResident.reportDate,
                    householdCompositionResident.changeReasonCode))
            .fetch();
    }
}
