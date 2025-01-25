package com.companystrator.company.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateCompanyDTO(

    @JsonProperty("name")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "Company name must be 5 to 70 and can only contain letters, numbers, spaces or points(.)", regexp = "^[a-zA-Z0-9 .]{5,70}$")
    String name,

    @JsonProperty("address")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "Company address must be 10 to 150 and can only contain letters, numbers, spaces or special characters(#, -)", regexp = "^[a-zA-Z0-9 .#-]{10,150}$")
    String address,

    @JsonProperty("phone_indicator")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "must start with a '+' followed by 1 to 3 digits", regexp = "^\\+([0-9]{1,3})$")
    String phoneIndicator,

    @JsonProperty("phone")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "must be a valid phone number with 7 to 15 digits", regexp = "^[0-9]{7,15}$")
    String phone

) { }
