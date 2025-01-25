package com.companystrator.product.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ProductCategoryDTO(

    @JsonProperty("id")
    Long id,

    @JsonProperty("category")
    String category

) { }
