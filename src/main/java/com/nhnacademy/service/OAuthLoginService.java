package com.nhnacademy.service;

import com.nhnacademy.dto.resident.ResidentUserDetails;
import com.nhnacademy.dto.security.oauth.GitHubUser;
import com.nhnacademy.exceptions.ResidentNotFoundExceprtion;
import com.nhnacademy.repository.resident.ResidentRepository;
import com.nhnacademy.security.SuccessHandler;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@PropertySource("classpath:github.oauth.properties")
@Slf4j
public class OAuthLoginService {
    private static final ConcurrentHashMap<String, String> stateStorage = new ConcurrentHashMap<>();

    private final RestTemplate restTemplate;
    private final ResidentRepository residentRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${github.oauth.client_id}")
    private String clientId;
    @Value("${github.oauth.client_secret}")
    private String clientSecret;

    public OAuthLoginService(RestTemplate restTemplate, ResidentRepository residentRepository,
                             RedisTemplate<String, String> redisTemplate) {
        this.restTemplate = restTemplate;
        this.residentRepository = residentRepository;
        this.redisTemplate = redisTemplate;
    }

    public String tryGithubOAuthLogin() {
        String tmpState = UUID.randomUUID().toString();
        stateStorage.put(tmpState, tmpState);

        UriComponents codeUri = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("github.com")
            .path("/login/oauth/authorize")
            .queryParam("client_id", clientId)
            .queryParam("state", tmpState)
            .queryParam("scope", "user")
            .build();


        return codeUri.toString();
    }

    public Map<String, Object> accessTokenProvider(String code, String state,
                                                   HttpServletResponse response)
        throws IOException {
        log.debug("concurrent hash map: " + stateStorage.get(state));
        log.debug("response state : " + state);

        String savedState = stateStorage.get(state);

        stateStorage.remove(state);

        if (!Objects.equals(savedState, state)) {
            stateStorage.remove(state);
            response.sendRedirect("/login");
            return Collections.EMPTY_MAP;
        }

        UriComponents accessTokenUri = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("github.com")
            .path("/login/oauth/access_token")
            .queryParam("code", code)
            .queryParam("client_id", clientId)
            .queryParam("client_secret", clientSecret)
            .queryParam("state", state)
            .build();


        Map gitHubResponse = restTemplate.postForObject(accessTokenUri.toString(), null, Map.class);
        log.debug("{}", new HashMap<String, Object>(gitHubResponse));

        return new HashMap<String, Object>(gitHubResponse);

    }

    public GitHubUser getResidentGithubFromToken(Map<String, Object> tokenResult) {
        String accessToken = tokenResult.get("access_token").toString();

        UriComponents accessTokenUri = UriComponentsBuilder.newInstance()
            .scheme("https")
            .host("api.github.com")
            .path("/user")
            .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + accessToken);
        HttpEntity httpEntity = new HttpEntity(headers);

        log.debug("header : " + headers.getFirst("Authorization"));

        ResponseEntity<GitHubUser> responseEntity =
            restTemplate.exchange(accessTokenUri.toString(), HttpMethod.GET, httpEntity,
                GitHubUser.class);

        log.debug("user Info : " + responseEntity.getBody());

        return responseEntity.getBody();
    }

    public void verifyResidentFromToken(String email, HttpServletRequest request,
                                        HttpServletResponse response)
        throws ServletException, IOException {
        ResidentUserDetails residentUserDetails = residentRepository.findResidentByEmail(email)
            .orElseThrow(() -> new ResidentNotFoundExceprtion("이메일에 알맞는 주민이 없습니다."));

        Authentication authentication =
            getAuthentication(residentUserDetails);

        successOAuthHandler(request, response, authentication);
    }

    private void successOAuthHandler(HttpServletRequest request, HttpServletResponse response,
                           Authentication authentication) throws ServletException, IOException {
        new SuccessHandler(redisTemplate).onAuthenticationSuccess(request, response,
            authentication);
    }

    private Authentication getAuthentication(ResidentUserDetails residentUserDetails) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_MEMBER");
        Collection<? extends GrantedAuthority> authorities =
            Collections.singletonList(grantedAuthority);
        UserDetails userDetails =
            new User(residentUserDetails.getUserId(), residentUserDetails.getPassword(),
                authorities);

        Authentication authentication =
            new UsernamePasswordAuthenticationToken(userDetails, "USER_PASSWORD",
                authorities);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return authentication;
    }
}


