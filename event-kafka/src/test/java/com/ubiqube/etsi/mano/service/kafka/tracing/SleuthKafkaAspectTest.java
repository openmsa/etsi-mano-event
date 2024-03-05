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

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.adapter.ConvertingMessageListener;

import brave.Tracer;
import brave.Tracing;
import brave.kafka.clients.KafkaTracing;

@ExtendWith(MockitoExtension.class)
class SleuthKafkaAspectTest {
	@Mock
	private KafkaTracing kafkaTracing;

	SleuthKafkaAspect createService() {
		final Tracing tracing = Tracing.newBuilder().build();
		final Tracer localTracer = tracing.tracer();
		return new SleuthKafkaAspect(kafkaTracing, localTracer);
	}

	@Test
	void base() throws Throwable {
		final SleuthKafkaAspect srv = createService();
		final ProceedingJoinPoint pjp = Mockito.mock(ProceedingJoinPoint.class);
		srv.wrapListenerContainerCreation(pjp);
		assertTrue(true);
	}

	@Test
	void base01() throws Throwable {
		final SleuthKafkaAspect srv = createService();
		final ProceedingJoinPoint pjp = Mockito.mock(ProceedingJoinPoint.class);
		final AbstractMessageListenerContainer amlc = Mockito.mock(AbstractMessageListenerContainer.class);
		when(pjp.proceed()).thenReturn(amlc);
		final ContainerProperties cp = Mockito.mock(ContainerProperties.class);
		when(amlc.getContainerProperties()).thenReturn(cp);
		srv.wrapListenerContainerCreation(pjp);
		assertTrue(true);
	}

	@Test
	void base02() throws Throwable {
		final SleuthKafkaAspect srv = createService();
		final ProceedingJoinPoint pjp = Mockito.mock(ProceedingJoinPoint.class);
		final AbstractMessageListenerContainer amlc = Mockito.mock(AbstractMessageListenerContainer.class);
		when(pjp.proceed()).thenReturn(amlc);
		final ContainerProperties cp = Mockito.mock(ContainerProperties.class);
		when(amlc.getContainerProperties()).thenReturn(cp);
		final ConvertingMessageListener cml = Mockito.mock(ConvertingMessageListener.class);
		when(cp.getMessageListener()).thenReturn(cml);
		srv.wrapListenerContainerCreation(pjp);
		assertTrue(true);
	}

	@Test
	void base03() throws Throwable {
		final SleuthKafkaAspect srv = createService();
		final ProceedingJoinPoint pjp = Mockito.mock(ProceedingJoinPoint.class);
		final AbstractMessageListenerContainer amlc = Mockito.mock(AbstractMessageListenerContainer.class);
		when(pjp.proceed()).thenReturn(amlc);
		final ContainerProperties cp = Mockito.mock(ContainerProperties.class);
		when(amlc.getContainerProperties()).thenReturn(cp);
		final ConvertingMessageListener cml = Mockito.mock(ConvertingMessageListener.class);
		when(cp.getMessageListener()).thenReturn("");
		srv.wrapListenerContainerCreation(pjp);
		assertTrue(true);
	}
}
