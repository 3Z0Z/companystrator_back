package com.companystrator.order.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record OrderItemDTO(

    @JsonProperty("product_code")
    int productCode,

    @JsonProperty("quantity")
    int quantity,

    @JsonProperty("amount")
    double amount

) { }
