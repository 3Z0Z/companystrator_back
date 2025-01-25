package com.companystrator.order.dto.req;

import com.companystrator.db.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record PlaceOrderDTO(

    @JsonProperty("NIT")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "Invalid NIT format. It must contain only numbers and have 8 to 10 digits", regexp = "^[0-9]{8,10}$")
    String nit,

    @JsonProperty("currency")
    Currency currency,

    @JsonProperty("order_items")
    @Valid
    List<PlaceOrderItemDTO> orderItems

) { }
