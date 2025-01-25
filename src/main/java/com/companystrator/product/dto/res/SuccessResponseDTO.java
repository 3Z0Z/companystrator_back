package com.companystrator.product.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SuccessResponseDTO(

    @JsonProperty("message")
    String message

) { }
