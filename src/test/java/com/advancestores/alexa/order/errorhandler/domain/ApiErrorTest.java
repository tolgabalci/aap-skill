package com.advancestores.alexa.order.errorhandler.domain;

import com.advancestores.alexa.order.domain.AbstractJavaBeanTester;

public class ApiErrorTest extends AbstractJavaBeanTester<ApiError> {

    public ApiError getBeanInstance() {
        return ApiError.builder().code(1001).message("error").build();
    }
}
