package com.advancestores.alexa.order.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResponseBodyAdviceAdapterTest {

	@BeforeEach
	public void setup(){
		MDC.clear();
	}

	@Test
	void supports() {
		ResponseBodyAdviceAdapter a = new ResponseBodyAdviceAdapter();
		assertTrue(a.supports(null, null));
	}

	@Test
	void beforeBodyWrite_400() {
		ResponseBodyAdviceAdapter a = new ResponseBodyAdviceAdapter();
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setStatus(404);
		ServletServerHttpResponse servletResponse = new ServletServerHttpResponse(response);
		assertEquals("responseSample", a.beforeBodyWrite("responseSample", null, MediaType.APPLICATION_JSON, null, null, servletResponse));
		assertEquals("404", MDC.get("httpStatus"));
	}

	@Test
	void beforeBodyWrite_200() {
		ResponseBodyAdviceAdapter a = new ResponseBodyAdviceAdapter();
		MockHttpServletResponse response = new MockHttpServletResponse();
		response.setStatus(200);
		ServletServerHttpResponse servletResponse = new ServletServerHttpResponse(response);
		assertEquals("responseSample", a.beforeBodyWrite("responseSample", null, MediaType.APPLICATION_JSON, null, null, servletResponse));
		assertEquals("200", MDC.get("httpStatus"));
	}
}