package com.nhnacademy.config;

import com.nhnacademy.security.SuccessHandler;
import com.nhnacademy.service.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity(debug = true)
public class SecureConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            .antMatchers(HttpMethod.GET, "/residents").hasAuthority("ROLE_MEMBER")
            .antMatchers(HttpMethod.GET, "/residents/*").hasAuthority("ROLE_MEMBER")
            .antMatchers("/certificate/*").hasAuthority("ROLE_MEMBER")
            .antMatchers("/certifications").hasAuthority("ROLE_MEMBER")
            .antMatchers("/issue/*").hasAuthority("ROLE_MEMBER")
            .anyRequest().permitAll()
        .and()
        .requiresChannel()
            .antMatchers(HttpMethod.GET, "/residents").requiresSecure()
            .antMatchers(HttpMethod.GET, "/residents/*").requiresSecure()
            .antMatchers("/certificate/*").requiresSecure()
            .antMatchers("/certifications").requiresSecure()
            .antMatchers("/issue/*").requiresSecure()
            .anyRequest().requiresInsecure()
        .and()
        .formLogin()
            .loginPage("/login")
            .usernameParameter("id")
            .passwordParameter("pw")
            .failureUrl("/login")
            .successHandler(loginSuccessHandler(null))
            .defaultSuccessUrl("/residents")
        .and()
        .logout()
            .logoutUrl("logout")
            .clearAuthentication(true)
            .deleteCookies("JSESSIONID", "session")
            .invalidateHttpSession(true)
        .and()
            .csrf().disable() // REST API를 위해서 일부러 끔.
        .sessionManagement()
            .sessionFixation().none() // 기존 세션 유지
        .and()
        .headers()                           // 브라우저 헤더 설정
            .defaultsDisabled()              // 기존 헤더 옵션 취소
                .frameOptions().sameOrigin()     // 같은 origin이면 iframe 태그 허용
    //            .httpStrictTransportSecurity() // https로 강제 접속 옵션
    //                .includeSubDomains(true)   // 서브 도메인까지 포함할것인가?
    //                .maxAgeInSeconds()         // 최대 몇초 이 설정을 유지할 것인가?
                .xssProtection().block(true)
            .and()
                .cacheControl().disable()        // 캐시를 직접 컨트롤할것인가?
                .contentTypeOptions()            // 컨텐츠 타입을 "MIME"형식으로 설정하여 무분별한 스니핑 방지.
            .and()
        .and()
            .exceptionHandling().accessDeniedPage("/403"); // 403에러 발생시 페이지 설정
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider(null));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
        CustomUserDetailsService customUserDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler(RedisTemplate<String, String> redisTemplate){
        return new SuccessHandler(redisTemplate);
    }
}
