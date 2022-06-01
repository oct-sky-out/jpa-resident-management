package com.nhnacademy.controller;

import com.nhnacademy.dto.birthreport.BirthIssueDto;
import com.nhnacademy.dto.birthreport.BirthResidentParentDto;
import com.nhnacademy.dto.certificateissue.RegisterCertificateIssueDto;
import com.nhnacademy.dto.deathreport.DeathIssueDto;
import com.nhnacademy.service.IssueService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/issue")
public class IssuesController {
    private final IssueService issueService;

    public IssuesController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/birth")
    public String showBirthIssue(@RequestParam("serialNumber") Long serialNumber,
                                 Model model) {

        BirthIssueDto birthIssue = issueService.residentBirthIssue(serialNumber);
        List<BirthResidentParentDto> residentParents =
            issueService.findResidentParents(serialNumber);

        model.addAttribute("birthIssue", birthIssue);
        model.addAttribute("parents", residentParents);

        return "issue/birth";
    }

    @GetMapping("/death")
    public String showDeathIssue(@RequestParam("serialNumber") Long serialNumber, Model model) {
        DeathIssueDto deathIssueDto = issueService.findResidentDeathIssue(serialNumber);
        model.addAttribute("deathIssue", deathIssueDto);

        return "issue/death";
    }
}
