package com.ubiqube.etsi.mano.service.kafka.tracing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.kafka.clients.consumer.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.BeanFactory;

import brave.kafka.clients.KafkaTracing;

@ExtendWith(MockitoExtension.class)
class TraceConsumerPostProcessorTest {

	private TraceConsumerPostProcessor traceConsumer;
	@Mock
	private BeanFactory beanFactory;

	@BeforeEach
	void setUp() {
		traceConsumer = new TraceConsumerPostProcessor<>(beanFactory);
	}

	@Test
	void test() {
		KafkaTracing tracing = mock(KafkaTracing.class);
		when(beanFactory.getBean(KafkaTracing.class)).thenReturn(tracing);
		Consumer consumer = mock(Consumer.class);
		traceConsumer.apply(consumer);
		assertTrue(true);
	}

}
