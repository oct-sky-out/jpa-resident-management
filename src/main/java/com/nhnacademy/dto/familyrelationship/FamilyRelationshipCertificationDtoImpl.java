package com.nhnacademy.dto.familyrelationship;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
public class FamilyRelationshipCertificationDtoImpl implements FamilyRelationshipCertificationDto{
    private String relationshipCode;

    private String name;

    private LocalDateTime birthDate;

    @Setter
    private String registrationNumber;

    private String genderCode;

    public FamilyRelationshipCertificationDtoImpl(FamilyRelationshipCertificationDto certificationDto) {
        this.relationshipCode = certificationDto.getRelationshipCode();
        this.name = certificationDto.getName();
        this.birthDate = certificationDto.getBirthDate();
        this.registrationNumber = certificationDto.getRegistrationNumber();
        this.genderCode = certificationDto.getGenderCode();
    }

    public void hideRegistrationBackNumber(){
        this.registrationNumber = this.registrationNumber
            .substring(0, 6)
            .concat("-XXXXXXX");
    }
}
