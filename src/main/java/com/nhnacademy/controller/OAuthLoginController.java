package com.nhnacademy.controller;

import com.nhnacademy.dto.security.oauth.GitHubUser;
import com.nhnacademy.exceptions.OAuthLoginProcessFailureException;
import com.nhnacademy.service.OAuthLoginService;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login/oauth2")
@Slf4j
public class OAuthLoginController {
    private final OAuthLoginService oAuthLoginService;

    public OAuthLoginController(OAuthLoginService oAuthLoginService) {
        this.oAuthLoginService = oAuthLoginService;
    }

    @GetMapping
    public void tryProcessGithubOAuthLogin(HttpServletResponse response,
                                           HttpServletRequest request) {
        try {
            response.sendRedirect(oAuthLoginService.tryGithubOAuthLogin());
        } catch (IOException e) {
            log.error("{}", e);
            throw new OAuthLoginProcessFailureException("OAuth provider 사용자 로그인 요청을 실패했습니다.");
        }
    }

    @GetMapping("/code/github")
    public void receiveCodeRequestAccessTokenToProvider(@RequestParam("code") String code,
                                                        @RequestParam("state") String state,
                                                        HttpServletResponse response,
                                                        HttpServletRequest request) {
        try {
            Map<String, Object> tokenResult =
                oAuthLoginService.accessTokenProvider(code, state, response);
            GitHubUser gitHubUser = oAuthLoginService.getResidentGithubFromToken(tokenResult);
            oAuthLoginService.verifyResidentFromToken(gitHubUser.getEmail(), request, response);
        } catch (IOException | ServletException e) {
            log.error("{}", e);
            throw new OAuthLoginProcessFailureException("OAuth provider access token 발급에 실패했습니다.");
        }
    }
}
