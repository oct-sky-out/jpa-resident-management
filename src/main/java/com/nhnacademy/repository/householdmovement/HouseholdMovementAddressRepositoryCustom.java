package com.nhnacademy.repository.householdmovement;

import com.nhnacademy.dto.housemovementaddress.HouseMovementAddressViewDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface HouseholdMovementAddressRepositoryCustom {
    List<HouseMovementAddressViewDto> findHouseMovementAddressesBy(Long householdSerial);
}
