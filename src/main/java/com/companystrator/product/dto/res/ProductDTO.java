package com.companystrator.product.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ProductDTO(

    @JsonProperty("code")
    int code,

    @JsonProperty("name")
    String name,

    @JsonProperty("description")
    String description,

    @JsonProperty("price_cop")
    double priceCop,

    @JsonProperty("price_usd")
    double priceUsd,

    @JsonProperty("price_mxn")
    double priceMxn,

    @JsonProperty("primary_category")
    String primaryCategory,

    @JsonProperty("secondary_category")
    String secondaryCategory

) { }
