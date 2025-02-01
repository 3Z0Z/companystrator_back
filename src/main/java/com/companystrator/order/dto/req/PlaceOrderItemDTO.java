package com.companystrator.order.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlaceOrderItemDTO(

    @JsonProperty("product_code")
    @NotNull(message = "is mandatory")
    @Digits(integer = 10, fraction = 0, message = "Code must be exactly 10 digits")
    Long productCode,

    @JsonProperty("quantity")
    @Positive(message = "Product amount USD must be greater than zero")
    int quantity

) { }
