package com.nhnacademy.dto.housemovementaddress;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HouseMovementAddressViewDto {
    private final String address;

    private final String lastAddress;

    private final LocalDate reportDate;
}
