package com.companystrator.product.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

public record CreateProductDTO(

    @JsonProperty("code")
    @NotNull(message = "is mandatory")
    @Digits(integer = 10, fraction = 0, message = "Code must be exactly 10 digits")
    int code,

    @JsonProperty("name")
    @Pattern(message = "Product name must be 10 to 20 characters, allowed letters, numbers, spaces, and special characters(.-)", regexp = "^[a-zA-Z0-9 .-]{10,20}$")
    String name,

    @JsonProperty("description")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "Product name must be 10 to 80 characters, allowed letters, numbers, spaces, and special characters(.-)", regexp = "^[a-zA-Z0-9 .-]{10,80}$")
    String description,

    @JsonProperty("price_cop")
    @Positive(message = "Price in COP must be greater than zero")
    double priceCop,

    @JsonProperty("price_usd")
    @Positive(message = "Price in USD must be greater than zero")
    double priceUsd,

    @JsonProperty("price_mxn")
    @Positive(message = "Price in MXN must be greater than zero")
    double priceMxn,

    @JsonProperty("primary_category")
    @NotNull(message = "is mandatory")
    Long primaryCategory,

    @JsonProperty("secondary_category")
    Long secondaryCategory,

    @JsonProperty("NIT")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "Invalid NIT format. It must contain only numbers and have 8 to 10 digits", regexp = "^[0-9]{8,10}$")
    String nit

) { }
