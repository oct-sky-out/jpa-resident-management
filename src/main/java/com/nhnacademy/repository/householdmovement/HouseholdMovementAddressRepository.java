package com.nhnacademy.repository.householdmovement;

import com.nhnacademy.dto.housemovementaddress.HouseMovementAddressViewDto;
import com.nhnacademy.entity.HouseholdMovementAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdMovementAddressRepository extends JpaRepository<HouseholdMovementAddress, HouseholdMovementAddress.Pk>,
    HouseholdMovementAddressRepositoryCustom{
    @Override
    List<HouseMovementAddressViewDto> findHouseMovementAddressesBy(Long householdSerial);
}
