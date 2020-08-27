/**
 * $Id$
 */
package com.advancestores.alexa.order.config;

import io.micrometer.NewRelicRegistryConfig;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import io.micrometer.newrelic.NewRelicRegistry;
import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Export spring-boot and custom metrics to New Relic.
 * <p>Provide New Relic Insights insert API key and the registered service name. Refer ocp/*.yaml</p>
 *
 * @see <a href="https://github.com/newrelic/micrometer-registry-newrelic">New Relic Micrometer</a>
 */
@Configuration
@AutoConfigureBefore({
        CompositeMeterRegistryAutoConfiguration.class,
        SimpleMetricsExportAutoConfiguration.class
})
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnClass(NewRelicRegistry.class)
@ConditionalOnProperty({"ENABLE_NEWRELIC", "NEWRELIC_INSIGHTS_INSERT_KEY"})
@Slf4j
public class NewRelicMetricsExportConfig {

    @Bean
    public NewRelicRegistryConfig newRelicConfig(@Value("${NEWRELIC_INSIGHTS_INSERT_KEY}") String apiKey, @Value("${NEWRELIC_APPNAME}") String appName) {
        log.info("NR metrics exporter is configured for {}", appName);

        return new NewRelicRegistryConfig() {
            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public String apiKey() {
                return apiKey;
            }

            @Override
            public Duration step() {
                return Duration.ofSeconds(5);
            }

            @Override
            public String serviceName() {
                return appName;
            }

        };
    }

    @Bean
    public NewRelicRegistry newRelicMeterRegistry(NewRelicRegistryConfig config) {
        NewRelicRegistry newRelicRegistry = NewRelicRegistry.builder(config).build();
        newRelicRegistry.start(new NamedThreadFactory("newrelic.micrometer.registry"));
        return newRelicRegistry;
    }
}
