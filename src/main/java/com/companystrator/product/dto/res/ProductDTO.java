package com.companystrator.product.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ProductDTO(

    @JsonProperty("code")
    Long code,

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
    Long primaryCategory,

    @JsonProperty("secondary_category")
    Long secondaryCategory

) { }
