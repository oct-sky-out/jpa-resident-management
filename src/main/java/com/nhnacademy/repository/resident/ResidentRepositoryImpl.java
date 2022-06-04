package com.nhnacademy.repository.resident;

import com.nhnacademy.dto.resident.ResidentUserDetails;
import com.nhnacademy.dto.resident.ResidentViewDto;
import com.nhnacademy.entity.QBirthDeathReportResident;
import com.nhnacademy.entity.QResident;
import com.nhnacademy.entity.Resident;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class ResidentRepositoryImpl extends QuerydslRepositorySupport implements ResidentRepositoryCustom{
    public ResidentRepositoryImpl() {
        super(Resident.class);
    }

    @Override
    public Page<ResidentViewDto> getAllResidentByPage(Pageable pageable) {
        QResident resident = QResident.resident;
        QBirthDeathReportResident birthDeathReportResident =
            QBirthDeathReportResident.birthDeathReportResident;

        List<ResidentViewDto> query = from(birthDeathReportResident)
            .rightJoin(birthDeathReportResident.resident, resident)
            .on(birthDeathReportResident.pk.birthDeathTypeCode.eq("출생"))
            .select(Projections.constructor(ResidentViewDto.class,
                resident.serialNumber,
                resident.name,
                resident.genderCode,
                birthDeathReportResident.pk.birthDeathTypeCode.isNotNull(),
                resident.deathDate.isNotNull()
            ))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(query, pageable, () -> query.size() + 1);
    }

    @Override
    public Optional<ResidentUserDetails> findUserDetailsByUsername(String username) {
        QResident resident = QResident.resident;

        return Optional.ofNullable(from(resident)
            .where(resident.userId.eq(username))
            .select(Projections.constructor(ResidentUserDetails.class,
                resident.serialNumber,
                resident.name,
                resident.userId,
                resident.password,
                resident.email))
            .fetchOne());
    }
}
