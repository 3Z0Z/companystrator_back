package com.companystrator.user.service.impl;

import com.companystrator.db.model.User;
import com.companystrator.db.repository.UserRepository;
import com.companystrator.user.service.LogoutUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LogoutUserServiceImpl implements LogoutUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length == 0) return;
        Cookie refreshCookie = Arrays.stream(cookies).filter(cookie -> "refresh_token_companystrator".equals(cookie.getName()))
            .findFirst().orElse(null);
        if (refreshCookie == null) return;
        User storedSession = this.userRepository.findByRefreshToken(refreshCookie.getValue()).orElse(null);
        if (storedSession != null) {
            storedSession.setRefreshToken(null);
            this.userRepository.saveAndFlush(storedSession);
            SecurityContextHolder.clearContext();
        }
    }

}
