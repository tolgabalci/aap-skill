/*
package com.advancestores.alexa.order.controller;


import static com.advancestores.alexa.order.controller.CatalogController.CATALOG_RELATIVE_PATH;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.oneOf;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import com.advancestores.alexa.order.errorhandler.RestExceptionHandler;
import com.advancestores.alexa.order.exception.ServiceException;

import com.advancestores.alexa.order.interceptor.RequestInterceptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.advancestores.alexa.order.domain.CatalogSummary;

import javax.servlet.RequestDispatcher;


@Execution(ExecutionMode.SAME_THREAD)
@WebMvcTest(CatalogController.class)
@Import(RestExceptionHandler.class)
public class CatalogControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CatalogService service;


    @Test
    void listSummaries_custom_accept_header() throws Exception {
        Mockito.when(service.retrieveSummaries("domain")).thenReturn(Optional.of(Arrays.asList(new CatalogSummary("summary1"), new CatalogSummary("summary2"))));

        mvc.perform(get("/" + CATALOG_RELATIVE_PATH + "/category/domain")
                .contentType(VersionedMediaTypes.CATALOG_CONTENT_TYPE_JSON_1_0))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].summary", is("summary1")))
                .andExpect(jsonPath("$[1].summary", is("summary2")));

        Mockito.verify(service).retrieveSummaries("domain");

    }

    @Test
    public void listSummaries_no_accept_header() throws Exception {
        Mockito.when(service.retrieveSummaries("domain")).thenReturn(Optional.of(Arrays.asList(new CatalogSummary("summary1"), new CatalogSummary("summary2"))));

        mvc.perform(get("/" + CATALOG_RELATIVE_PATH + "/category/domain"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].summary", is("summary1")))
                .andExpect(jsonPath("$[1].summary", is("summary2")));

        Mockito.verify(service).retrieveSummaries("domain");
    }

    @Test
    public void listSummaries_404() throws Exception {
        Mockito.when(service.retrieveSummaries("invalid")).thenReturn(Optional.empty());
        mvc.perform(get("/" + CATALOG_RELATIVE_PATH + "/category/invalid"))
                .andExpect(status().isNotFound())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().exists(RequestInterceptor.HEADER_X_CORRELATION_ID))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors",hasSize(1)))
                .andExpect(jsonPath("$.errors[0].code", is(1004)))
                .andExpect(jsonPath("$.errors[0].message", is("No results were found for the specified request: invalid")))
        ;

        Mockito.verify(service).retrieveSummaries("invalid");
    }

    @Test
    public void listSummaries_500() throws Exception {
        Mockito.when(service.retrieveSummaries("domain")).thenThrow(new ServiceException("error"));
        mvc.perform(get("/" + CATALOG_RELATIVE_PATH + "/category/domain"))
                .andExpect(status().is5xxServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().exists(RequestInterceptor.HEADER_X_CORRELATION_ID))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors",hasSize(1)))
                .andExpect(jsonPath("$.errors[0].code", is(0)))
                .andExpect(jsonPath("$.errors[0].message", is("Service exception: error")));

        Mockito.verify(service).retrieveSummaries("domain");
    }

    @Test
    public void listSummaries_bad_request() throws Exception {
        mvc.perform(get("/" + CATALOG_RELATIVE_PATH +"/cat1/ABC"))
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().exists(RequestInterceptor.HEADER_X_CORRELATION_ID))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors",hasSize(2)))
                .andExpect(jsonPath("$.errors[0].code", is(1001)))
                .andExpect(jsonPath("$.errors[0].message", oneOf("cat1 is not a valid value for getSummaries.category", "ABC is not a valid value for getSummaries.domain")))
                .andExpect(jsonPath("$.errors[1].code", is(1001)))
                .andExpect(jsonPath("$.errors[1].message", oneOf("cat1 is not a valid value for getSummaries.category", "ABC is not a valid value for getSummaries.domain")))

        ;

        Mockito.verify(service, Mockito.never()).retrieveSummaries("domain");
    }

    @Test
    public void listSummaries_invalid_accept_header() throws Exception {
        mvc.perform(get("/" + CATALOG_RELATIVE_PATH + "/category/domain")
                .header(HttpHeaders.ACCEPT, "application/json1"))
                .andExpect(status().isNotAcceptable())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        ;

        Mockito.verify(service, Mockito.never()).retrieveSummaries("domain");
    }

    @Test
    public void fallback_error_handler_406() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 406)
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "bad accept header")
                .accept("application/vnd.com.advancestores.alexa-order+json; v=222;charset=UTF-8"))
                .andExpect(status().isNotAcceptable())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().exists(RequestInterceptor.HEADER_X_CORRELATION_ID))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors",hasSize(1)))
                .andExpect(jsonPath("$.errors[0].code", is(1006)))
                .andExpect(jsonPath("$.errors[0].message", startsWith("No match for accept header:")));
    }

    @Test
    public void fallback_error_handler_404() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404)
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "error"))
                .andExpect(status().isNotFound())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().exists(RequestInterceptor.HEADER_X_CORRELATION_ID))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors",hasSize(1)))
                .andExpect(jsonPath("$.errors[0].code", is(1004)))
                .andExpect(jsonPath("$.errors[0].message", is("No results were found for the specified request: error")))
        ;
    }

    @Test
    public void fallback_error_handler_400() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "bad request"))
                .andExpect(status().isBadRequest())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().exists(RequestInterceptor.HEADER_X_CORRELATION_ID))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors",hasSize(1)))
                .andExpect(jsonPath("$.errors[0].code", is(1005)))
                .andExpect(jsonPath("$.errors[0].message", is("Invalid request: bad request")))
        ;
    }

    @Test
    public void fallback_error_handler_503() throws Exception {
        mvc.perform(get("/error").requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 503)
                .requestAttr(RequestDispatcher.ERROR_MESSAGE, "severe error"))
                .andExpect(status().isInternalServerError())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(header().exists(RequestInterceptor.HEADER_X_CORRELATION_ID))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors",hasSize(1)))
                .andExpect(jsonPath("$.errors[0].code", is(0)))
                .andExpect(jsonPath("$.errors[0].message", is("Service exception: Status: 503 Error: severe error")))
        ;
    }


}*/
