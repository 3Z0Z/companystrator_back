package com.companystrator.company.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SuccessResponseDTO(

    @JsonProperty("message")
    String message

) { }
