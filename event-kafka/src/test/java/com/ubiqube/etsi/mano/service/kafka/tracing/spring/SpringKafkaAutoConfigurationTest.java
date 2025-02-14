package com.ubiqube.etsi.mano.service.kafka.tracing.spring;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class SpringKafkaAutoConfigurationTest {

	@Test
	void test() {
		SpringKafkaAutoConfiguration springKafkaAutoConfiguration = new SpringKafkaAutoConfiguration();
		assertNotNull(springKafkaAutoConfiguration);
		springKafkaAutoConfiguration.tracingKafkaAspect(null, null);
		springKafkaAutoConfiguration.kafkaFactoryBeanPostProcessor(null);
	}

}
