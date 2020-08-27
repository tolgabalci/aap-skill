package com.advancestores.alexa.order.integration.kafka;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;


class ProductMessageFilterTest {

	private static Schema schema;

	@BeforeAll
	public static void classSetup() throws IOException {
		schema = new Schema.Parser().parse(new ClassPathResource("avro/CatalogSummaryMessage.avsc").getFile());
	}

	@Test
	public void filter_valid_message() {
		GenericRecord record = new GenericData.Record(schema);
		record.put("summary", "valid");
		ProductMessageFilter f = new ProductMessageFilter();

		ConsumerRecord consumerRecord = Mockito.mock(ConsumerRecord.class);
		Mockito.when(consumerRecord.value()).thenReturn(record);
		Assertions.assertFalse(f.filter(consumerRecord));

		Mockito.verify(consumerRecord).value();
	}

	@Test
	public void filter_invalid_message(){
		GenericRecord record = new GenericData.Record(schema);
		record.put("summary", "invalid");
		ProductMessageFilter f = new ProductMessageFilter();

		ConsumerRecord consumerRecord = Mockito.mock(ConsumerRecord.class);
		Mockito.when(consumerRecord.value()).thenReturn(record);
		Assertions.assertTrue(f.filter(consumerRecord));

		Mockito.verify(consumerRecord).value();
	}

}