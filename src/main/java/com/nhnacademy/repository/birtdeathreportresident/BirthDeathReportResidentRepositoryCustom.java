package com.nhnacademy.repository.birtdeathreportresident;

import com.nhnacademy.dto.deathreport.DeathIssueDto;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BirthDeathReportResidentRepositoryCustom {
    DeathIssueDto findResidentDeathIssueBy(Long serialNumber);
}
