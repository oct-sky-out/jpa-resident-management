package com.nhnacademy.service;

import com.nhnacademy.dto.birthreport.BirthIssueDto;
import com.nhnacademy.dto.birthreport.BirthResidentParentDto;
import com.nhnacademy.dto.deathreport.DeathIssueDto;
import com.nhnacademy.repository.birtdeathreportresident.BirthDeathReportResidentRepository;
import com.nhnacademy.repository.familyrelationship.FamilyRelationshipRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class IssueService {
    private final BirthDeathReportResidentRepository birthDeathReportResidentRepository;
    private final FamilyRelationshipRepository familyRelationshipRepository;

    public IssueService(BirthDeathReportResidentRepository birthDeathReportResidentRepository,
                        FamilyRelationshipRepository familyRelationshipRepository) {
        this.birthDeathReportResidentRepository = birthDeathReportResidentRepository;
        this.familyRelationshipRepository = familyRelationshipRepository;
    }

    public BirthIssueDto residentBirthIssue(Long serialNumber){
        return birthDeathReportResidentRepository.findResidentBirthIssueBy(serialNumber);
    }

    public List<BirthResidentParentDto> findResidentParents(Long serialNumber){
        return familyRelationshipRepository.findResidentParents(serialNumber)
            .stream().map(birthResidentParentDto -> {
                birthResidentParentDto.hideRegistrationBackNumber();
                return birthResidentParentDto;
            })
            .collect(Collectors.toList());
    }

    public DeathIssueDto findResidentDeathIssue(Long serialNumber){
        DeathIssueDto deathIssue =
            birthDeathReportResidentRepository.findResidentDeathIssueBy(serialNumber);
        deathIssue.hideRegistrationBackNumber();

        return deathIssue;
    }
}
