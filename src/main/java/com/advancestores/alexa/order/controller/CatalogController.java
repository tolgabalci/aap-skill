/**
 * $Id$
 */
package com.advancestores.alexa.order.controller;

import com.advancestores.alexa.order.errorhandler.domain.ApiErrors;
import com.advancestores.alexa.order.exception.NotFoundException;
import com.advancestores.alexa.order.exception.ServiceException;
import com.advancestores.alexa.order.domain.CatalogSummary;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

import java.util.List;
import java.util.Objects;

import static com.advancestores.alexa.order.controller.VersionedMediaTypes.CATALOG_CONTENT_TYPE_JSON_1_0;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Rest Controller
 *
 * @author $Author$ (last modified by)
 * @version $Revision$
 */
@RestController
@Validated
@Slf4j
public class CatalogController implements ErrorController {

	private static final String ERROR_URL = "${server.error.path:${error.path:/error}}";

	/**
	 * Path name prefix for catalog resources. Naming is primarily for
	 * backwards compatibility with the original omni-channel catalog Java EE
	 * service.
	 */
	//TODO: Update Relative path
	public static final String CATALOG_RELATIVE_PATH = "catalog";

	/**
	 * Defines the name of the resource as it should appear in API documentation.
	 */
	//TODO: Update Resource tag
	public static final String RESOURCE_TAG = "TODO";

	/**
	 * Defines the description of the resource as it should appear in API
	 * documentation.
	 */
	//TODO: Update Resource description
	public static final String RESOURCE_DESCRIPTION = "TODO";



	/*	@GetMapping(value = "/" + CATALOG_RELATIVE_PATH + "/{category}/{domain}", produces = { APPLICATION_JSON_VALUE, CATALOG_CONTENT_TYPE_JSON_1_0 })
		@Operation(description = "//TODO: Add description", summary = "Catalog Summaries", tags = {"catalog"})
		@ApiResponse(responseCode = "200", description = "Successful",
				content = @Content(schema = @Schema(implementation = CatalogSummary.class)))
		@ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiErrors.class)))
		@ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ApiErrors.class)))
		@ApiResponse(responseCode = "406", description = "Invalid Accept header", content = @Content(schema = @Schema(implementation = ApiErrors.class)))
		@ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content(schema = @Schema(implementation = ApiErrors.class)))
		public List<CatalogSummary> getSummaries(
				@Parameter(name = "category", in = ParameterIn.PATH, description = "Catalog category", example = "category", required = true)
				@PathVariable("category") @Pattern(regexp = "[a-zA-Z]*", message = "category.invalid") String category,
				@Parameter(name = "domain", in = ParameterIn.PATH, description = "Catalog domain", example = "domain1", required = true)
				@PathVariable("domain") @Pattern(regexp = "[a-z1-9]*", message = "domain.invalid") String domain) {
			return service.retrieveSummaries(domain)
					.orElseThrow(() -> new NotFoundException(domain));
		}*/

	/**
	 * Global exception handler for
	 * <ul>
	 *     <li>Unhandled application exception</li>
	 *     <li>Tomcat white page error</li>
	 * </ul>
	 *
	 * @param request
	 * @throws HttpMediaTypeNotAcceptableException
	 */
	@GetMapping(value = ERROR_URL)
	@Operation(description = "error", hidden = true)
	public void handleError(HttpServletRequest request) throws HttpMediaTypeNotAcceptableException {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (Objects.nonNull(status)) {
			Object objErrorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
			String errorMessage = StringUtils.defaultString((String)objErrorMessage);

			log.error("Status: {}. Error: {}", status, errorMessage);

			int statusCode = Integer.parseInt(status.toString());

			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				throw new NotFoundException(errorMessage);
			} else if (statusCode == HttpStatus.NOT_ACCEPTABLE.value()) {
				throw new HttpMediaTypeNotAcceptableException(errorMessage);
			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				throw new HttpMessageNotReadableException(errorMessage);
			} else {
				throw new ServiceException("Status: " + status + " Error: " + StringUtils.defaultString(errorMessage));
			}
		}
	}

	@Override
	public String getErrorPath() {
		return ERROR_URL;
	}

}
