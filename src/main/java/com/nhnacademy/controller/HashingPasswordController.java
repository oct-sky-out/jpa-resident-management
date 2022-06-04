package com.nhnacademy.controller;

import com.nhnacademy.dto.SuccessDto;
import com.nhnacademy.entity.Resident;
import com.nhnacademy.service.security.HashingService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("residents/hash/password")
public class HashingPasswordController {
    private final HashingService hashingService;

    public HashingPasswordController(HashingService hashingService) {
        this.hashingService = hashingService;
    }

    /**
     * 기존에 플레인 텍스트로 저장된 유저들의 비밀번호를 암호화 시키기 위한 컨트롤러
     * 기존 유저들의 비밀번호 리스트
     * gil1234
     * seok1234
     * han1234
     * gi1234
     * jue1234
     * seo1234
     * ki1234
     */
    @GetMapping
    public SuccessDto<List<Resident>> modifyResidentsPassword(){
        return hashingService.hashResidentsPassword();
    }
}
