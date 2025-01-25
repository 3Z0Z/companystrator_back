package com.companystrator.order.dto.res;

import com.companystrator.db.enums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record OrderDetailedDTO(

    @JsonProperty("order_id")
    Long orderId,

    @JsonProperty("client_id")
    Long clientId,

    @JsonProperty("total_amount")
    double totalAmount,

    @JsonProperty("currency")
    Currency currency,

    @JsonProperty("order_items")
    List<OrderItemDTO> orderItems,

    @JsonProperty("placet_at")
    Date placetAt

) { }
