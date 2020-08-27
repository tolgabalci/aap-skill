package com.advancestores.alexa.order.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

	public static final String HEADER_X_CORRELATION_ID = "x-correlation-id";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		interceptParameters(request);
		printHeaders(request);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		MDC.clear();
	}

	private void interceptParameters(HttpServletRequest request){
		if(StringUtils.isNotEmpty(request.getRequestURI())) {
			//Intercept Request params
			if(request.getParameterNames().hasMoreElements()) {
				Collections.list(request.getParameterNames())
						.forEach(param -> MDC.put(param, StringUtils.defaultString(request.getParameter(param))));
			}

			//Intercept path params
			final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			if (pathVariables != null && !pathVariables.isEmpty()) {
				pathVariables.forEach((var, val) -> MDC.put(var, StringUtils.defaultString(val)));
			}

			MDC.put("requestUri", request.getRequestURI());
			MDC.put(HEADER_X_CORRELATION_ID, StringUtils.defaultString(request.getHeader(HEADER_X_CORRELATION_ID)));
		}
	}

	private void printHeaders(HttpServletRequest request) {
		if(log.isTraceEnabled()) {
			log.trace("Request headers: {}", Collections.list(request.getHeaderNames()).stream().map(h -> h + ": " + request.getHeader(h)).collect(Collectors.joining(",")));
		}
	}
}
