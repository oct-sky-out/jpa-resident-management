package com.nhnacademy.controller;

import com.nhnacademy.command.BirthIssueCommand;
import com.nhnacademy.command.Command;
import com.nhnacademy.command.FamilyCertCommand;
import com.nhnacademy.dto.certificateissue.CertificationTitleDto;
import com.nhnacademy.dto.certificateissue.RegisterCertificateIssueDto;
import com.nhnacademy.dto.familyrelationship.FamilyRelationshipCertificationDto;
import com.nhnacademy.dto.household.HouseholdViewDto;
import com.nhnacademy.dto.householdcomposition.HouseholdCompositionViewDto;
import com.nhnacademy.dto.housemovementaddress.HouseMovementAddressViewDto;
import com.nhnacademy.entity.CertificateIssue;
import com.nhnacademy.exceptions.HasNotFamilyException;
import com.nhnacademy.service.CertificateIssueService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/certificate")
public class CertificateIssueController {

    private final CertificateIssueService certificateIssueService;

    public CertificateIssueController(CertificateIssueService certificateIssueService) {
        this.certificateIssueService = certificateIssueService;
    }

    @PostMapping
    public String beIssuedCertificate(@ModelAttribute RegisterCertificateIssueDto registerCertificateIssue){
        CertificateIssue certification = certificateIssueService.registerCertificate(registerCertificateIssue);

        return certificateIssueService.getRedirectionUrl(certification, registerCertificateIssue);
    }

    @GetMapping("/family/{confirmationNumber}")
    public String showFamilyRelationshipCertification(@PathVariable("confirmationNumber") Long confirmationNumber,
                                                      Model model){
        // 상단 조회 영역 조회
        CertificationTitleDto title = Optional.ofNullable(certificateIssueService.certificationTitle(confirmationNumber))
            .orElseThrow(() -> new HasNotFamilyException("가족정보가 없습니다."));
        // 가족관계 출력
        List<FamilyRelationshipCertificationDto> familyCertification = certificateIssueService.getFamilyRelationshipCertification(title.getSerialNumber());

        model.addAttribute("title", title);
        model.addAttribute("relations", familyCertification);

        return "certification/familyRelaionCertification";
    }

    @GetMapping("/household/{confirmationNumber}")
    public String showHouseholdCertification(@PathVariable("confirmationNumber") Long confirmationNumber,
                                             @RequestParam("serialNumber") Long serialNumber,
                                             Model model){
        // 상단 조회 영역 조회
        CertificationTitleDto title = certificateIssueService.certificationTitle(confirmationNumber);
        // 세대주 번호 조회
        Long householdSerialNumber = certificateIssueService.findHouseholdSerialNumber(serialNumber);
        // 세대주 정보 조회
        HouseholdViewDto household = certificateIssueService.findHousehold(householdSerialNumber);
        // 세대 구성 주민
        List<HouseholdCompositionViewDto> householdCompositions =
            certificateIssueService.findHouseCompositionResidents(householdSerialNumber);
        // 세대주 이사 이력
        List<HouseMovementAddressViewDto> houseMovementAddresses =
            certificateIssueService.findHouseMoventsRecord(householdSerialNumber);

        model.addAttribute("title", title);
        model.addAttribute("householdCompositions", householdCompositions);
        model.addAttribute("household", household);
        model.addAttribute("houseMovementAddresses", houseMovementAddresses);

        return "certification/householdCertification";
    }
}
