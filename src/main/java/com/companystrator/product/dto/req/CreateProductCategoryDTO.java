package com.companystrator.product.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateProductCategoryDTO(

    @JsonProperty("category")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "Must be 5 to 20 characters, letters, numbes and special characters(_)", regexp = "^[a-zA-Z0-9_]{5,20}$")
    String category

) { }
