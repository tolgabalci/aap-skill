package com.advancestores.alexa.order.config;

import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.util.backoff.ExponentialBackOff;

@Configuration
@Profile("kafka")
public class KafkaConfig {

    @Bean
    @Description("Kafka consumer configuration with concurrency, error handler")
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory, SeekToCurrentErrorHandler seekToCurrentErrorHandler,
            RecordFilterStrategy productMessageFilter) {

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        factory.setErrorHandler(seekToCurrentErrorHandler);
        factory.setRecordFilterStrategy(productMessageFilter);
        return factory;
    }

    @Bean
    @Description("Route poison message to DLT and commit the offset. Retry logic. Added other exceptions that should not be retried")
    SeekToCurrentErrorHandler seekToCurrentErrorHandler(KafkaTemplate<Object, Object> template){
        SeekToCurrentErrorHandler seekToCurrentErrorHandler = new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(template), new ExponentialBackOff());
        seekToCurrentErrorHandler.addNotRetryableException(NullPointerException.class);
        seekToCurrentErrorHandler.addNotRetryableException(IllegalArgumentException.class);
        seekToCurrentErrorHandler.setAckAfterHandle(true);
        return seekToCurrentErrorHandler;
    }
}
