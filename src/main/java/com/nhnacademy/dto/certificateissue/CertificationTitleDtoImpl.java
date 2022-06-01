package com.nhnacademy.dto.certificateissue;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CertificationTitleDtoImpl implements CertificationTitleDto{
    private String code;

    private Long serialNumber;

    private LocalDate issueDate;

    private String confirmationNumber;

    private String baseAddress;
}
