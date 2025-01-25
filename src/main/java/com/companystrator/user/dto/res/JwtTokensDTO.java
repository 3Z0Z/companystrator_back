package com.companystrator.user.dto.res;

import lombok.Builder;

@Builder
public record JwtTokensDTO(

    String token,
    String refreshToken

) { }
