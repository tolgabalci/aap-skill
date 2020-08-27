package com.advancestores.alexa.order.interceptor;

import com.advancestores.alexa.order.controller.CatalogController;
import com.advancestores.alexa.order.controller.VersionedMediaTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerMapping;

import java.util.HashMap;
import java.util.Map;


class RequestInterceptorTest {

	@BeforeEach
	public void setup(){
		MDC.clear();
	}

	@Test
	void preHandle_valid_accept() {
		RequestInterceptor interceptor = new RequestInterceptor();
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.addHeader(HttpHeaders.ACCEPT, VersionedMediaTypes.CATALOG_CONTENT_TYPE_JSON_1_0);
		req.addHeader(RequestInterceptor.HEADER_X_CORRELATION_ID, "1234");
		req.setRequestURI("/"+CatalogController.CATALOG_RELATIVE_PATH);
		req.setParameter("a", "val");
		Map<String, String> pathVariables = new HashMap<>();
		pathVariables.put("domain", "product");
		req.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, pathVariables);
		Assertions.assertTrue(interceptor.preHandle(req, new MockHttpServletResponse(), new Object()));
		Assertions.assertEquals("/"+CatalogController.CATALOG_RELATIVE_PATH, MDC.get("requestUri"));
		Assertions.assertEquals("1234", MDC.get(RequestInterceptor.HEADER_X_CORRELATION_ID));
		Assertions.assertEquals("val", MDC.get("a"));
		Assertions.assertEquals("product", MDC.get("domain"));
		MDC.clear();

		req = new MockHttpServletRequest();
		req.addHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		Assertions.assertTrue(interceptor.preHandle(req, new MockHttpServletResponse(), new Object()));

		req = new MockHttpServletRequest();
		Assertions.assertTrue(interceptor.preHandle(req, new MockHttpServletResponse(), new Object()));
	}

	@Test
	void preHandle_invalid_accept() {
		RequestInterceptor interceptor = new RequestInterceptor();
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.setRequestURI("/"+CatalogController.CATALOG_RELATIVE_PATH);
		req.addHeader(HttpHeaders.ACCEPT, "application/vnd.com.advancestores.alexa-order+json;v=2;charset=UTF-8");
		Assertions.assertTrue(interceptor.preHandle(req, new MockHttpServletResponse(), new Object()));

		req = new MockHttpServletRequest();
		req.setRequestURI("/"+CatalogController.CATALOG_RELATIVE_PATH);
		req.addHeader(HttpHeaders.ACCEPT, "text/xml");
		Assertions.assertTrue(interceptor.preHandle(req, new MockHttpServletResponse(), new Object()));

	}
}