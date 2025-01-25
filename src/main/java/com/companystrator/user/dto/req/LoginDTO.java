package com.companystrator.user.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginDTO(

		@JsonProperty("username")
        @NotNull(message = "is mandatory")
        @Pattern(message = "must have between 5 and 20 characters and no special sing", regexp = "^[a-zA-Z0-9]{5,20}$")
        String username,

        @JsonProperty("password")
        @NotNull(message = "is mandatory")
        String password

) {}
