package com.advancestores.alexa.order.errorhandler.domain;

import com.advancestores.alexa.order.domain.AbstractJavaBeanTester;

import java.util.List;

public class ApiErrorsTest extends AbstractJavaBeanTester<ApiErrors> {

    public ApiErrors getBeanInstance() {
        return ApiErrors.builder().errors(List.of(ApiError.builder().code(100).message("error").build(), ApiError.builder().code(200).message("error1").build())).build();
    }
}
