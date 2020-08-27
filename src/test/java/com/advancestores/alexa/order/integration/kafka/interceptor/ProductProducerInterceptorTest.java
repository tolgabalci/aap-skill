package com.advancestores.alexa.order.integration.kafka.interceptor;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;


class ProductProducerInterceptorTest {

	private static Schema schema;

	@BeforeAll
	public static void classSetup() throws IOException {
		schema = new Schema.Parser().parse(new ClassPathResource("avro/CatalogSummaryMessage.avsc").getFile());
	}

	@Test
	void onSend() {
		GenericRecord record = new GenericData.Record(schema);
		record.put("summary", "valid");
		ProducerRecord producerRecord = new ProducerRecord("key", record);

		ProductProducerInterceptor i = new ProductProducerInterceptor();
		ProducerRecord producerRecord1 = i.onSend(producerRecord);
		Assertions.assertEquals(producerRecord, producerRecord1);

	}
}