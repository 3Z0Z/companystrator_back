package com.companystrator.order.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SuccessResponseDTO(

    @JsonProperty("message")
    String message

) { }
