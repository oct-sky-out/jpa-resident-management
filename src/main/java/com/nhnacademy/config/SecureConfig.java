package com.nhnacademy.config;

import com.nhnacademy.security.SuccessHandler;
import com.nhnacademy.service.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity(debug = true)
public class SecureConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.GET, "/residents").hasAuthority("ROLE_MEMBER")
                .antMatchers(HttpMethod.GET, "/residents/*").hasAuthority("ROLE_MEMBER")
                .antMatchers("/certificate/*").hasAuthority("ROLE_MEMBER")
                .antMatchers("/certifications").hasAuthority("ROLE_MEMBER")
                .antMatchers("/issue/*").hasAuthority("ROLE_MEMBER")
                .anyRequest().permitAll()
            .and()
            .requiresChannel()
//            .antMatchers(HttpMethod.GET, "/residents").requiresSecure()
//            .antMatchers(HttpMethod.GET, "/residents/*").requiresSecure()
//            .antMatchers("/certificate/*").requiresSecure()
//            .antMatchers("/certifications").requiresSecure()
//            .antMatchers("/issue/*").requiresSecure()
                .anyRequest().requiresInsecure()
            .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("id")
                .passwordParameter("pw")
                .failureUrl("/login")
                .defaultSuccessUrl("/residents")
                .successHandler(loginSuccessHandler(null))
            .and()
            .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .deleteCookies("session")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
            .and()
                .csrf().disable() // REST API??? ????????? ????????? ???.
                .sessionManagement()
                .sessionFixation().none() // ?????? ?????? ??????
            .and()
                .headers()                           // ???????????? ?????? ??????
                    .defaultsDisabled()              // ?????? ?????? ?????? ??????
                    .frameOptions().sameOrigin()     // ?????? origin?????? iframe ?????? ??????
                    .httpStrictTransportSecurity()// https??? ?????? ?????? ??????
                        .disable()
            //                .includeSubDomains(true)   // ?????? ??????????????? ???????????????????
            //                .maxAgeInSeconds()         // ?????? ?????? ??? ????????? ????????? ??????????
                    .xssProtection().block(true)
                .and()
                    .cacheControl().disable()        // ????????? ?????? ??????????????????????
                    .contentTypeOptions()            // ????????? ????????? "MIME"???????????? ???????????? ???????????? ????????? ??????.
                .and()
            .and()
                .exceptionHandling().accessDeniedPage("/403") // 403?????? ????????? ????????? ??????
            .and()
            .authenticationProvider(authenticationProvider(null));

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService customUserDetailsService) {
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
    public AuthenticationSuccessHandler loginSuccessHandler(
        RedisTemplate<String, String> redisTemplate) {
        return new SuccessHandler(redisTemplate);
    }
}
