/**
 * $Id$
 */
package com.advancestores.alexa.order.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Initializes the application&apos;s usage of Swagger to expose API documentation for consumers
 * of the service.
 * 
 * @version $Revision$
 */
@Configuration
public class SwaggerConfig {
    /**
     * Builds Swagger API documentation containing title and description for the service.
     * 
     * @return APIInfo instance
     */

    @Bean
    public OpenAPI vehicleOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("alexa-order Service")
                        .description("alexa-order Service")
                        .version("1.0")
                        .contact(new Contact()
                                .name("OCC team")
                                .url("https://advanceautoparts.atlassian.net/wiki/x/MILbMw")
                                .email("831bb37c.advanceauto.onmicrosoft.com@amer.teams.ms")))
                .externalDocs(new ExternalDocumentation()
                        .description("Technical reference")
                        .url("")); //TODO: Add reference url
    }
}
