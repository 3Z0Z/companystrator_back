package com.companystrator.user.controller;

import com.companystrator.user.dto.req.CreateUserDTO;
import com.companystrator.user.dto.req.LoginDTO;
import com.companystrator.user.dto.res.JwtTokensDTO;
import com.companystrator.user.dto.res.SuccessResponseDTO;
import com.companystrator.user.dto.res.TokenResponseDTO;
import com.companystrator.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<SuccessResponseDTO> createUser(@RequestBody @Valid CreateUserDTO request) {
        this.userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new SuccessResponseDTO("User " + request.username() + " created successfully")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> createVisitor(@RequestBody @Valid LoginDTO request) {
        JwtTokensDTO token = this.userService.login(request);
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token_companystrator", token.refreshToken())
            .httpOnly(true)
            //.secure(true); Habilitar si se puede enviar por HTTPS
            .path("/")
            .maxAge(30L * 24L * 60L * 60L)
            .sameSite("Strict")
            .build();
        return ResponseEntity.status(HttpStatus.OK)
            .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
            .body(new TokenResponseDTO(token.token()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponseDTO> refreshToken(HttpServletRequest request) {
        TokenResponseDTO tokenResponseDTO = this.userService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponseDTO);
    }

}
