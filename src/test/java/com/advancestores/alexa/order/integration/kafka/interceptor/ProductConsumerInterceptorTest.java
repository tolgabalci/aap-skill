package com.advancestores.alexa.order.integration.kafka.interceptor;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ProductConsumerInterceptorTest {

	@Test
	void onConsume() {
		ProductConsumerInterceptor i = new ProductConsumerInterceptor();

		ConsumerRecords records = Mockito.mock(ConsumerRecords.class);

		Mockito.when(records.count()).thenReturn(2);

		ConsumerRecords consumerRecords = i.onConsume(records);
		Assertions.assertEquals(records, consumerRecords);
		Mockito.verify(records).count();
	}
}