package com.nhnacademy.repository.household;

import com.nhnacademy.dto.household.HouseholdViewDto;
import com.nhnacademy.entity.Household;
import com.nhnacademy.entity.QHousehold;
import com.querydsl.core.types.Projections;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class HouseholdRepositoryImpl extends QuerydslRepositorySupport implements HouseholdRepositoryCustom{
    public HouseholdRepositoryImpl() {
        super(Household.class);
    }

    @Override
    public Optional<HouseholdViewDto> findBy(Long householdSerial) {
        QHousehold household = QHousehold.household;

        return Optional.ofNullable(
            from(household)
                .where(household.serialNumber.eq(householdSerial))
                .select(Projections.constructor(HouseholdViewDto.class,
                    household.resident.name,
                    household.compositionReasonCode,
                    household.compositionDate))
                .fetchOne()
        );
    }
}
