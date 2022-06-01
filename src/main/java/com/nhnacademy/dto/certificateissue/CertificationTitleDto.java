package com.nhnacademy.dto.certificateissue;

import java.time.LocalDate;

public interface CertificationTitleDto {
    String getCode();

    Long getSerialNumber();

    LocalDate getIssueDate();

    String getConfirmationNumber();

    String getBaseAddress();
}
