package com.companystrator.user.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SuccessResponseDTO(

    @JsonProperty("message")
    String message

) { }
