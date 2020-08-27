package com.advancestores.alexa.order.integration.kafka;

import com.advancestores.alexa.order.domain.CatalogSummary;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka consumer and dead-letter-topic listeners for avro message
 */
@Service
@Slf4j
@Profile("kafka")
public class ProductKafkaConsumer {

    @KafkaListener(topicPattern = "${kafka.topics.product.name}", concurrency = "${kafka.topics.product.concurrency:1}", id = "${kafka.topics.product.group}")
    public void consume(ConsumerRecord<String, CatalogSummary> catalogRecord){
        log.info("Consumed message: {}", catalogRecord);
        //TODO: Add message processor logic
    }

    /**
     * Consume from dead letter topic. For testing purpose only.
     */
    @KafkaListener(id = "dltGroup", topics = "#{'${kafka.topics.product.name}' + '.DLT'}")
    public void consumePoisonMessage(ConsumerRecord<String, GenericRecord> poisonRecord) {
        log.error("Consumed poison message: {}", poisonRecord);
        //TODO: Handle poison message
    }
}
