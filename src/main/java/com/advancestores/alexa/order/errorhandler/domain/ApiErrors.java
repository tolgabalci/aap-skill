package com.advancestores.alexa.order.errorhandler.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.List;

/**
 * @see <a href="https://advanceautoparts.atlassian.net/wiki/x/mwc6Tg">Error handling guidelines</a>
 */
@Data
@Builder
@Schema(description = "Collection of ApiError")
public class ApiErrors implements Serializable {

    @NonNull
    private List<ApiError> errors;

}
