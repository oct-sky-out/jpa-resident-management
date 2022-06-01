package com.nhnacademy.dto.familyrelationship;

import java.time.LocalDateTime;

public interface FamilyRelationshipCertificationDto {
    String getRelationshipCode();

    String getName();

    LocalDateTime getBirthDate();

    String getRegistrationNumber();

    String getGenderCode();
}
