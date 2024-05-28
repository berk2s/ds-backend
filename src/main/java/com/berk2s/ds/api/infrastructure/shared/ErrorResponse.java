package com.berk2s.ds.api.infrastructure.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {
    @JsonProperty("error")
    private String error;

    @JsonProperty("errorDescription")
    private String errorDescription;

    @JsonProperty("details")
    private List<String> details;
}