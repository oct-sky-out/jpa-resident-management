package com.nhnacademy.service.security;


import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import com.nhnacademy.config.RootConfig;
import com.nhnacademy.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@Transactional
@WebAppConfiguration
@ContextHierarchy({
    @ContextConfiguration(classes = RootConfig.class),
    @ContextConfiguration(classes = WebConfig.class)
})
class HashingServiceTest {
    Logger log = (Logger) LoggerFactory.getLogger(HashingServiceTest.class);
    @Autowired
    HashingService service;

    @Test
    void hashResidentsPassword() {
        service.hashResidentsPassword()
            .getResult()
            .forEach(resident -> log.error(resident.getPassword()));
    }
}
