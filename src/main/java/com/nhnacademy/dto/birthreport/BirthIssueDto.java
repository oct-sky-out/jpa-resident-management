package com.nhnacademy.dto.birthreport;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface BirthIssueDto {
    LocalDate getReportDate();

    String getName();

    String getGenderCode();

    LocalDateTime getBirthDate();

    String getPlaceCode();

    String getBaseAddress();

    String getReporterName();

    String getReporterRegistrationNumber();

    String getQualificationCode();

    String getEmail();

    String getPhoneNumber();
}
