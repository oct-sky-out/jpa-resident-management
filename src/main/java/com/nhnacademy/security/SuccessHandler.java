package com.nhnacademy.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Slf4j
public class SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Long THREE_DAYS_AT_SECONDS = 259200L;

    private final RedisTemplate<String, String> redisTemplate;

    public SuccessHandler(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
        throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.error(userDetails.getUsername());
        log.error(new ArrayList<>(userDetails.getAuthorities()).toString());

        List<GrantedAuthority> authorities = new ArrayList<>(userDetails.getAuthorities());

        String username = userDetails.getUsername();
        String authority = authorities.get(0).getAuthority();
        HttpSession session = request.getSession(false);

        redisTemplate.opsForHash().put(session.getId(), "userId", username);
        redisTemplate.opsForHash().put(session.getId(), "authority", authority);
        redisTemplate.boundHashOps(session.getId()).expire(THREE_DAYS_AT_SECONDS, TimeUnit.SECONDS);

        session.setMaxInactiveInterval(THREE_DAYS_AT_SECONDS.intValue());
        session.setAttribute("userId", username);
        session.setAttribute("authority", authority);
    }
}
