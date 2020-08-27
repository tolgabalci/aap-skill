/**
 * $Id$
 */
package com.advancestores.alexa.order;

import com.advancestores.alexa.order.interceptor.RequestInterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Main boot strap class for enabling the service.
 *
 * @version $Revision$
 */
@EnableEncryptableProperties
@SpringBootApplication
public class CatalogMainApplication extends SpringBootServletInitializer implements WebMvcConfigurer {
    /**
     * Java main method for staring the application.
     * @param args - the standard Java args parameter.
     */
    @SuppressWarnings("squid:S4823")
    public static void main(String...args) {
        SpringApplication.run(CatalogMainApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CatalogMainApplication.class);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

}
