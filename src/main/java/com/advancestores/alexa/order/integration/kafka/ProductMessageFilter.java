package com.advancestores.alexa.order.integration.kafka;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

/**
 * Filter poison message
 */
@Component
@Profile("kafka")
public class ProductMessageFilter implements RecordFilterStrategy<String, GenericRecord> {

    @Override
    public boolean filter(ConsumerRecord<String, GenericRecord> consumerRecord) {
        return consumerRecord.value().get("summary").toString().equalsIgnoreCase("invalid");
    }
}
