package com.nhnacademy.repository.householdmovement;

import com.nhnacademy.dto.housemovementaddress.HouseMovementAddressViewDto;
import com.nhnacademy.entity.HouseholdMovementAddress;
import com.nhnacademy.entity.QHouseholdMovementAddress;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class HouseholdMovementAddressRepositoryImpl extends QuerydslRepositorySupport
    implements HouseholdMovementAddressRepositoryCustom{
    public HouseholdMovementAddressRepositoryImpl() {
        super(HouseholdMovementAddress.class);
    }

    @Override
    public List<HouseMovementAddressViewDto> findHouseMovementAddressesBy(Long householdSerial) {
        QHouseholdMovementAddress householdMovementAddress = QHouseholdMovementAddress.householdMovementAddress;

        return from(householdMovementAddress)
            .where(householdMovementAddress.pk.serialNumber.eq(householdSerial))
            .select(Projections.constructor(HouseMovementAddressViewDto.class,
                householdMovementAddress.address,
                householdMovementAddress.lastAddress,
                householdMovementAddress.pk.reportDate))
            .orderBy(householdMovementAddress.pk.reportDate.desc())
            .fetch();
    }
}
