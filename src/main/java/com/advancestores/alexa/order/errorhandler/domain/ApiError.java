package com.advancestores.alexa.order.errorhandler.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;


@Data
@Builder
@Schema(description = "ApiError: This entity encapsulates a known error response (client side 4xx error) that " +
        "may be emitted by the service, typically in scenarios where the data passed is either invalid " +
        "or missing.")
public class ApiError implements Serializable {

    @Schema(description = "Custom error message describing the reason for the error response.")
    @NonNull
    private String message;

    @Schema(description = "AAP internal error code")
    @Nullable
    private int code;

}