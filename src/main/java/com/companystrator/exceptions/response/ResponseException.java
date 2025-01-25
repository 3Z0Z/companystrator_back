package com.companystrator.exceptions.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseException(

	@JsonProperty("error")
	String error

) { }
