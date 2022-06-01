package com.nhnacademy.dto.certificateissue;

import com.nhnacademy.entity.Resident;
import java.time.LocalDate;

public interface CertificationIssueRecordDto {
    String getCode();

    LocalDate getIssueDate();

    String getName();
}
