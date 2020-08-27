package com.advancestores.alexa.order.integration.kafka.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Map;

@Slf4j
public class ProductConsumerInterceptor implements ConsumerInterceptor<String, GenericRecord> {

    @Override
    public ConsumerRecords onConsume(ConsumerRecords consumerRecords) {
        log.debug("Intercepted {} messages", consumerRecords.count());
        log.trace("Partitions: {}", consumerRecords.partitions().toString());
        return consumerRecords;
    }

    @Override
    public void close() {
    }

    @Override
    public void onCommit(Map map) {
    }

    @Override
    public void configure(Map<String, ?> map) {
    }
}
