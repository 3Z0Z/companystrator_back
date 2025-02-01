package com.companystrator.user.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateUserDTO(

    @JsonProperty("username")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "must be 5 to 20 characters long and can only contain letters and numbers", regexp = "^[a-zA-Z0-9]{5,20}$")
    String username,

    @JsonProperty("password")
    @NotBlank(message = "is mandatory")
    @Pattern(message = "must have at least 10 to 50 characters, a number and a special sing", regexp = "^(?=.*\\d)(?=.*[-_*?!@/().#=])[A-Za-z\\d-_*?!@/().#=]{10,50}$")
    String password,

    @JsonProperty("email")
    @NotBlank(message = "is mandatory")
    @Email(message = "invalid email format")
    String email

) { }
