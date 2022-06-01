package com.nhnacademy.dto.familyrelationship;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterFamilyRelationshipDto {
    @NotNull
    private Long familySerialNumber;

    @NotBlank
    private String relationShip;
}
