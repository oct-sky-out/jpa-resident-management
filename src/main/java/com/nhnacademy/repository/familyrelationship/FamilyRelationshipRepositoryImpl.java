package com.nhnacademy.repository.familyrelationship;

import com.nhnacademy.dto.birthreport.BirthResidentParentDto;
import com.nhnacademy.entity.FamilyRelationship;
import com.nhnacademy.entity.QFamilyRelationship;
import com.nhnacademy.entity.QResident;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class FamilyRelationshipRepositoryImpl extends QuerydslRepositorySupport implements FamilyRelationshipRepositoryCustom {

    public FamilyRelationshipRepositoryImpl() {
        super(FamilyRelationship.class);
    }

    @Override
    public List<BirthResidentParentDto> findResidentParents(Long serialNumber) {
        QFamilyRelationship familyRelationship = QFamilyRelationship.familyRelationship;
        QResident resident = QResident.resident;

        return from(familyRelationship)
            .join(familyRelationship.resident, resident)
            .where(resident.serialNumber.eq(serialNumber))
            .select(Projections.constructor(BirthResidentParentDto.class,
                familyRelationship.code,
                resident.name,
                resident.registrationNumber
                ))
            .fetch();
    }
}
