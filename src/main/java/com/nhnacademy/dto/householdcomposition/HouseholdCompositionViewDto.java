package com.nhnacademy.dto.householdcomposition;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class HouseholdCompositionViewDto {
    private final String name;

    private String registrationNumber;

    private final String code;

    private final LocalDate reportDate;

    private final String changeReasonCode;

    public void hideRegistrationBackNumber(){
        this.registrationNumber = this.registrationNumber
            .substring(0, 6)
            .concat("-XXXXXXX");
    }
}
