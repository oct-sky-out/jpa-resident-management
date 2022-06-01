package com.nhnacademy.service;

import com.nhnacademy.dto.certificateissue.CertificationIssueRecordDto;
import com.nhnacademy.entity.CertificateIssue;
import com.nhnacademy.repository.certificateissue.CertificateIssueRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CertificationRecordService {
    private final CertificateIssueRepository certificateIssueRepository;

    public CertificationRecordService(CertificateIssueRepository certificateIssueRepository) {
        this.certificateIssueRepository = certificateIssueRepository;
    }

    public Page<CertificationIssueRecordDto> findAllResidentCerts(Pageable pageable){
        return certificateIssueRepository.findAllCertificationByPageable(pageable);
    }
}
