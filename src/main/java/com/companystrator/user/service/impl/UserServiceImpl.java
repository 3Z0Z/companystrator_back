package com.companystrator.user.service.impl;

import com.companystrator.config.JwtService;
import com.companystrator.db.enums.UserRoles;
import com.companystrator.db.model.User;
import com.companystrator.db.repository.UserRepository;
import com.companystrator.exceptions.exception.NoCookiesFoundException;
import com.companystrator.exceptions.exception.TokenNotFoundOrExpiredException;
import com.companystrator.exceptions.exception.UsernameNotFoundException;
import com.companystrator.user.dto.req.CreateUserDTO;
import com.companystrator.user.dto.req.LoginDTO;
import com.companystrator.user.dto.res.JwtTokensDTO;
import com.companystrator.user.dto.res.TokenResponseDTO;
import com.companystrator.exceptions.exception.CreateUserException;
import com.companystrator.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public void createUser(CreateUserDTO request) {
        Optional<User> validateUserExist = this.userRepository.findByUsernameOrEmail(request.username(), request.email());
        if (validateUserExist.isPresent()) {
            log.error("username {} or email {} already exist", request.username(), request.email());
            throw new CreateUserException("Username " + request.username() + " or email " + request.email() + " already exist");
        }
        User newUser = User.builder()
            .username(request.username())
            .password(this.passwordEncoder.encode(request.password()))
            .email(request.email())
            .role(UserRoles.CLIENT)
            .build();
        this.userRepository.save(newUser);
    }

    @Override
    public JwtTokensDTO login(LoginDTO request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = this.userRepository.findByUsername(request.username())
            .orElseThrow(() ->  new UsernameNotFoundException("User not found with username " + request.username()));
        String refreshToken = this.jwtService.generateToken(user, true);
        String tokenJwt = this.jwtService.generateToken(user, false);
        user.setRefreshToken(refreshToken);
        this.userRepository.save(user);
        return JwtTokensDTO.builder().refreshToken(refreshToken).token(tokenJwt).build();
    }

    @Override
    public TokenResponseDTO refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) throw new NoCookiesFoundException("No cookies found in the request");
        Cookie cookieRefreshToken = Arrays.stream(cookies).filter(cookie -> "refresh_token_companystrator".equals(cookie.getName())).findFirst()
            .orElseThrow(() -> new NoCookiesFoundException("No refresh cookie found on request"));
        String refreshToken = cookieRefreshToken.getValue();
        if (!this.jwtService.isTokenNonExpired(refreshToken)) throw new TokenNotFoundOrExpiredException("Refresh token is expired");
        User user = this.userRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new TokenNotFoundOrExpiredException("Token not found"));
        return new TokenResponseDTO(this.jwtService.generateToken(user, false));
    }

}
