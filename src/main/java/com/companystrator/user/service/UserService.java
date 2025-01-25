package com.companystrator.user.service;

import com.companystrator.user.dto.req.CreateUserDTO;
import com.companystrator.user.dto.req.LoginDTO;
import com.companystrator.user.dto.res.JwtTokensDTO;
import com.companystrator.user.dto.res.TokenResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    void createUser(CreateUserDTO request);

    JwtTokensDTO login(LoginDTO request);

    TokenResponseDTO refreshToken(HttpServletRequest request);

}
