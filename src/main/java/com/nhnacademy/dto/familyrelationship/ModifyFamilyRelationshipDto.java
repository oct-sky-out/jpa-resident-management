package com.nhnacademy.dto.familyrelationship;

import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModifyFamilyRelationshipDto {
    @NotBlank
    private String relationShip;
}
