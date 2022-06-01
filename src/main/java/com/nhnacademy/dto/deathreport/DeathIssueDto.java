package com.nhnacademy.dto.deathreport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeathIssueDto {
    LocalDate reportDate;
    String name;
    String registrationNumber;
    LocalDateTime deathTime;
    String placeCode;
    String address;
    String reporterName;
    String reporterRegistrationNumber;
    String qualificationCode;
    String email;
    String phoneNumber;

    public void hideRegistrationBackNumber(){
        this.registrationNumber = this.registrationNumber
            .substring(0,6)
            .concat("-XXXXXXX");
        this.reporterRegistrationNumber = this.reporterRegistrationNumber
            .substring(0,6)
            .concat("-XXXXXXX");
    }
}
