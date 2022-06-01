package com.nhnacademy.dto.household;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HouseholdViewDto {
    private final String name;

    private final String compositionReasonCode;

    private final LocalDate compositionDate;
}
