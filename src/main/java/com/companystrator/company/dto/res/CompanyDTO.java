package com.companystrator.company.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CompanyDTO(

    @JsonProperty("NIT")
    String nit,

    @JsonProperty("name")
    String name,

    @JsonProperty("address")
    String address,

    @JsonProperty("phone_indicator")
    String phoneIndicator,

    @JsonProperty("phone")
    String phone

) { }
