package com.nhnacademy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.config.RootConfig;
import com.nhnacademy.config.WebConfig;
import com.nhnacademy.dto.certificateissue.CertificationTitleDto;
import com.nhnacademy.repository.certificateissue.CertificateIssueRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@Transactional
@ContextHierarchy({
    @ContextConfiguration(classes = RootConfig.class),
    @ContextConfiguration(classes = WebConfig.class)
})
class CertificateIssueRepositoryTest {
    @Autowired
    CertificateIssueRepository certificateIssueRepository;

    @Test
    void getCertificationTitleBySerialNumber() {
        CertificationTitleDto titleDto = certificateIssueRepository.getCertificationTitleBySerialNumber(1234567891011121L);

        assertThat(titleDto.getBaseAddress()).isEqualTo("경기도 성남시 분당구 대왕판교로645번길");
        assertThat(titleDto.getSerialNumber()).isEqualTo(4);
    }
}
