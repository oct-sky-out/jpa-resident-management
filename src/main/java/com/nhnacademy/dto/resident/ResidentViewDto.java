package com.nhnacademy.dto.resident;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResidentViewDto {
    private Long serialNumber;

    private String name;

    private String genderCode;

    private Boolean birthIssue;

    private Boolean deathIssue;
}
