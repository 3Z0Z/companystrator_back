package com.companystrator.user.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponseDTO(

    @JsonProperty("access_token")
    String accessToken

) { }
