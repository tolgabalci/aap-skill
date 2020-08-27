package com.advancestores.alexa.order.integration.kafka;

import com.advancestores.alexa.order.domain.CatalogSummary;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class ProductKafkaConsumerTest {

	private ProductKafkaConsumer consumer;

	@BeforeEach
	public void setup(){
		consumer = new ProductKafkaConsumer();
	}

	@Test
	void consume() {
		ConsumerRecord<String, CatalogSummary> consumerRecord = Mockito.mock(ConsumerRecord.class);
		consumer.consume(consumerRecord);
	}

	@Test
	void consumePoisonMessage() {
		ConsumerRecord<String, GenericRecord> consumerRecord = Mockito.mock(ConsumerRecord.class);
		consumer.consumePoisonMessage(consumerRecord);
	}
}