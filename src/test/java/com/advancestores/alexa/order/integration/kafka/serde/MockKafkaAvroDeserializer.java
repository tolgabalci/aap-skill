/*
package com.advancestores.alexa.order.integration.kafka.serde;

import com.advancestores.alexa.order.domain.CatalogSummaryMessage;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Schema;

import java.util.Map;

public class MockKafkaAvroDeserializer extends KafkaAvroDeserializer {

	@Override
	public Object deserialize(String topic, byte[] bytes) {
		this.schemaRegistry = getMockClient(CatalogSummaryMessage.SCHEMA$);
		return super.deserialize(topic, bytes);
	}

	private static SchemaRegistryClient getMockClient(final Schema schema$) {
		return new MockSchemaRegistryClient() {
			@Override
			public synchronized Schema getById(int id) {
				return schema$;
			}
		};
	}
}
*/
