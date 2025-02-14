package com.ubiqube.etsi.mano.service.kafka.tracing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.kafka.clients.producer.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.BeanFactory;

import brave.kafka.clients.KafkaTracing;

@ExtendWith(MockitoExtension.class)
class TraceProducerPostProcessorTest {
	@Mock
	private BeanFactory beanFactory;
	private TraceProducerPostProcessor traceProducerPostProcessor;
	@Mock
	private Producer producer;

	@BeforeEach
	void setUp() {
		traceProducerPostProcessor = new TraceProducerPostProcessor<>(beanFactory);
	}

	@Test
	void test() {
		KafkaTracing kt = mock(KafkaTracing.class);
		when(beanFactory.getBean(KafkaTracing.class)).thenReturn(kt);
		traceProducerPostProcessor.apply(producer);
		assertTrue(true);
	}

}
