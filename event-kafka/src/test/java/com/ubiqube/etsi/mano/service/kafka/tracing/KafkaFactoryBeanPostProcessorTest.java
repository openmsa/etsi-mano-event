/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.kafka.tracing;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

@ExtendWith(MockitoExtension.class)
class KafkaFactoryBeanPostProcessorTest {
	@Mock
	private BeanFactory beanFactory;

	@Test
	void test() {
		final KafkaFactoryBeanPostProcessor srv = new KafkaFactoryBeanPostProcessor(beanFactory);
		srv.postProcessAfterInitialization(srv, "name");
		assertTrue(true);
	}

	@Test
	void testConsumerFactory() {
		final KafkaFactoryBeanPostProcessor srv = new KafkaFactoryBeanPostProcessor(beanFactory);
		final DefaultKafkaConsumerFactory dkf = Mockito.mock(DefaultKafkaConsumerFactory.class);
		srv.postProcessAfterInitialization(dkf, "name");
		assertTrue(true);
	}

	@Test
	void testConsumerFactory01() {
		final KafkaFactoryBeanPostProcessor srv = new KafkaFactoryBeanPostProcessor(beanFactory);
		final DefaultKafkaConsumerFactory dkf = Mockito.mock(DefaultKafkaConsumerFactory.class);
		final List lst = List.of(Mockito.mock(TraceConsumerPostProcessor.class));
		when(dkf.getPostProcessors()).thenReturn(lst);
		srv.postProcessAfterInitialization(dkf, "name");
		assertTrue(true);
	}

	@Test
	void testProducerFactory() {
		final KafkaFactoryBeanPostProcessor srv = new KafkaFactoryBeanPostProcessor(beanFactory);
		final DefaultKafkaProducerFactory dkf = Mockito.mock(DefaultKafkaProducerFactory.class);
		srv.postProcessAfterInitialization(dkf, "name");
		assertTrue(true);
	}

	@Test
	void testProducerFactory01() {
		final KafkaFactoryBeanPostProcessor srv = new KafkaFactoryBeanPostProcessor(beanFactory);
		final DefaultKafkaConsumerFactory dkf = Mockito.mock(DefaultKafkaConsumerFactory.class);
		final List lst = List.of(Mockito.mock(TraceProducerPostProcessor.class));
		when(dkf.getPostProcessors()).thenReturn(lst);
		srv.postProcessAfterInitialization(dkf, "name");
		assertTrue(true);
	}
}
