/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
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
