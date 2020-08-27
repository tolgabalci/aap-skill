/*
package com.advancestores.alexa.order.integration.kafka;

import com.advancestores.alexa.order.domain.CatalogSummary;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

*/
/**
 * Publish avro message to kafka topic. Callback handler for success and failed message
 *//*

@Service
@Slf4j
@Profile("kafka")
public class ProductKafkaProducer {

    @Autowired
    private KafkaTemplate<String,  CatalogSummaryMessage> kafkaTemplate;

    @Value("${kafka.topics.product.name}")
    private String productTopic;

    public void publish(CatalogSummary catalog){
        kafkaTemplate.send(productTopic, UUID.randomUUID().toString(), CatalogSummaryMessage.newBuilder().setSummary(catalog.getSummary()).build())
                .addCallback(new ListenableFutureCallback<>() {
                    @Override
                    public void onFailure(Throwable e) {
                        log.error("Failed to publish message {} to topic: {}. Error: {}", catalog, productTopic, ExceptionUtils.getRootCauseMessage(e));
                    }

                    @Override
                    public void onSuccess(SendResult<String, CatalogSummaryMessage> stringCatalogSummarySendResult) {
                        log.info("Published product message");
                        log.trace("Published message {} to topic: {}", stringCatalogSummarySendResult, productTopic);
                    }
                });
    }
}
*/
