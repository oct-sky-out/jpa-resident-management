package com.nhnacademy.controller;

import com.nhnacademy.dto.resident.ResidentViewDto;
import com.nhnacademy.service.ResidentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/residents")
@Controller
public class ResidentViewController {
    private final ResidentService residentService;

    public ResidentViewController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @GetMapping
    public String showResidents(Pageable pageable, Model model){
        Page<ResidentViewDto> residents = residentService.searchAllResidentToPageable(pageable);
        model.addAttribute("residents", residents.getContent());
        model.addAttribute("hasNext", residents.hasNext());
        model.addAttribute("hasPrevious", residents.hasPrevious());
        model.addAttribute("currentPage", residents.getNumber());
        return "residentsView";
    }

    @GetMapping("/{serialNumber}")
    public String removeResident(@RequestParam("serialNumber") Long serialNumber){
        residentService.removeResident(serialNumber);
        return "redirect:/residents?page=0&size=5";
    }
}
