/*
package com.advancestores.alexa.order.integration.kafka;

import com.advancestores.alexa.order.config.KafkaConfig;
import com.advancestores.alexa.order.domain.CatalogSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

*/
/**
 * This test creates an embedded 1 broker kafka cluster once for this test class and not per testcase.
 * Covers all usecases of {@link ProductKafkaProducer} and {@link ProductKafkaConsumer} and {@link ProductMessageFilter}
 *//*

@EmbeddedKafka(
		partitions = 1,
		topics = {"${kafka.topics.product.name}", "${kafka.topics.product.name}.DLT"},
		controlledShutdown = true,
		brokerProperties = {"log.dir=tmp/kafka"})

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
		properties = {
				"kafka.topics.product.name=occ.hackathon",
				"kafka.topics.product.group=hackathon-consumer",

				"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
				"spring.kafka.properties.auto.register.schemas=true",
				"spring.kafka.properties.schema.registry.url=unused",

				"spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer",
				"spring.kafka.producer.value-serializer=com.advancestores.alexa.order.integration.kafka.serde.MockKafkaAvroSerializer",
				"spring.kafka.producer.acks=all",
				"spring.kafka.producer.properties.interceptor.classes=com.advancestores.alexa.order.integration.kafka.interceptor.ProductProducerInterceptor",

				"spring.kafka.consumer.value-deserializer=com.advancestores.alexa.order.integration.kafka.serde.MockKafkaAvroDeserializer",
				"spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer",
				"spring.kafka.consumer.group-id=hackathon-consumer",
				"spring.kafka.consumer.auto-offset-reset=earliest",
				"spring.kafka.consumer.enable-auto-commit=false",
				"spring.kafka.consumer.properties.specific.avro.reader=true",
				"spring.kafka.consumer.properties.interceptor.classes=com.advancestores.alexa.order.integration.kafka.interceptor.ProductConsumerInterceptor",
		},
		classes = {
				KafkaConfig.class,
				KafkaAutoConfiguration.class,
				ProductKafkaProducer.class,
				ProductKafkaConsumer.class,
				ProductMessageFilter.class
		}
)
@ActiveProfiles("kafka")
@Execution(ExecutionMode.SAME_THREAD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductEmbeddedKafkaTest {

	@Autowired
	private ProductKafkaProducer producer;


	@Test
	public void publish_consume_valid_message() throws InterruptedException {
		producer.publish(CatalogSummary.builder().summary("summary").build());
		Thread.sleep(50);
	}

	@Test
	public void publish_invalid_content_filterout() throws InterruptedException {
		producer.publish(CatalogSummary.builder().summary("invalid").build());
		Thread.sleep(50);
	}

}*/
