package com.nhnacademy.repository.familyrelationship;

import com.nhnacademy.dto.birthreport.BirthResidentParentDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface FamilyRelationshipRepositoryCustom {
    List<BirthResidentParentDto> findResidentParents(Long serialNumber);
}
