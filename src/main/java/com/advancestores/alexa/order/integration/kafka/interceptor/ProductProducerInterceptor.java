package com.advancestores.alexa.order.integration.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

@Slf4j
public class ProductProducerInterceptor implements ProducerInterceptor<String, GenericRecord> {

    @Override
    public ProducerRecord<String, GenericRecord> onSend(ProducerRecord<String, GenericRecord> producerRecord) {
        log.trace("Publishing {}", producerRecord);
        return producerRecord;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {

    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
