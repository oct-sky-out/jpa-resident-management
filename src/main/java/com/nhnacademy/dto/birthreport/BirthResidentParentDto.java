package com.nhnacademy.dto.birthreport;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BirthResidentParentDto {
    private String relationshipCode;
    private String name;
    private String registrationNumber;

    public void hideRegistrationBackNumber(){
        this.registrationNumber = this.registrationNumber
            .substring(0, 6)
            .concat("-XXXXXXX");
    }
}
