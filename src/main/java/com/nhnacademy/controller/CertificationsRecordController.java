package com.nhnacademy.controller;

import com.nhnacademy.dto.certificateissue.CertificationIssueRecordDto;
import com.nhnacademy.entity.CertificateIssue;
import com.nhnacademy.repository.certificateissue.CertificateIssueRepository;
import com.nhnacademy.service.CertificationRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/certifications")
public class CertificationsRecordController {
    private final CertificationRecordService certificationRecordService;

    public CertificationsRecordController(CertificationRecordService certificationRecordService) {
        this.certificationRecordService = certificationRecordService;
    }

    @GetMapping
    public String certificationsRecord(Pageable pageable, Model model){
        Page<CertificationIssueRecordDto> issues =
            certificationRecordService.findAllResidentCerts(pageable);

        model.addAttribute("certs", issues.getContent());
        model.addAttribute("hasNext", issues.hasNext());
        model.addAttribute("hasPrevious", issues.hasPrevious());
        model.addAttribute("currentPage", issues.getNumber());
        return "certificationRecords";
    }
}
