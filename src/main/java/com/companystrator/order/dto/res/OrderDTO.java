package com.companystrator.order.dto.res;

import com.companystrator.db.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Date;

@Builder
public record OrderDTO(

    @JsonProperty("order_id")
    Long orderId,

    @JsonProperty("client_id")
    Long clientId,

    @JsonProperty("total_amount")
    double totalAmount,

    @JsonProperty("currency")
    Currency currency,

    @JsonProperty("placet_at")
    Date placetAt

) { }
