package com.nhnacademy.repository.certificateissue;

import com.nhnacademy.dto.certificateissue.CertificationIssueRecordDto;
import com.nhnacademy.dto.certificateissue.CertificationTitleDto;
import com.nhnacademy.entity.CertificateIssue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CertificateIssueRepository extends JpaRepository<CertificateIssue, Long> {
    @Query("select c.code as code, c.resident.serialNumber as serialNumber, c.issueDate as issueDate, c.confirmationNumber as confirmationNumber, r.baseAddress as baseAddress" +
        "  from CertificateIssue c inner join c.resident r  where c.confirmationNumber = :confirmNumber")
    CertificationTitleDto getCertificationTitleBySerialNumber(@Param("confirmNumber") Long confirmNumber);
    @Query("select c.code as code, c.issueDate as issueDate, c.resident.name as name " +
        "from CertificateIssue c")
    Page<CertificationIssueRecordDto> findAllCertificationByPageable(Pageable pageable);
}
