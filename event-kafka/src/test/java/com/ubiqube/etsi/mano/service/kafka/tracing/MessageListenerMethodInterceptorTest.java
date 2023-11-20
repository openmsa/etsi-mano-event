/**
 *     Copyright (C) 2019-2023 Ubiqube.
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import brave.Tracer;
import brave.Tracing;
import brave.kafka.clients.KafkaTracing;

@ExtendWith(MockitoExtension.class)
class MessageListenerMethodInterceptorTest {
	@Mock
	private KafkaTracing kafkaTracing;
	@Mock
	private Tracer tracer;

	@Test
	void testBase() throws Throwable {
		final MessageListenerMethodInterceptor srv = new MessageListenerMethodInterceptor<>(kafkaTracing, tracer);
		final MethodInvocation inv = Mockito.mock(MethodInvocation.class);
		when(inv.getMethod()).thenReturn(getOnMessageMethod());
		//
		when(inv.getArguments()).thenReturn(new Object[0]);
		srv.invoke(inv);
		assertTrue(true);
	}

	@Test
	void testNotessage() throws Throwable {
		final MessageListenerMethodInterceptor srv = new MessageListenerMethodInterceptor<>(kafkaTracing, tracer);
		final MethodInvocation inv = Mockito.mock(MethodInvocation.class);
		when(inv.getMethod()).thenReturn(getAnyOtherMethod());
		//
		srv.invoke(inv);
		assertTrue(true);
	}

	@Test
	void testBaseRecord01() throws Throwable {
		final MessageListenerMethodInterceptor srv = new MessageListenerMethodInterceptor<>(kafkaTracing, tracer);
		final MethodInvocation inv = Mockito.mock(MethodInvocation.class);
		when(inv.getMethod()).thenReturn(getOnMessageMethod());
		//
		when(inv.getArguments()).thenReturn(new Object[1]);
		srv.invoke(inv);
		assertTrue(true);
	}

	@Test
	void testBaseRecord02() throws Throwable {
		final MessageListenerMethodInterceptor srv = new MessageListenerMethodInterceptor<>(kafkaTracing, tracer);
		final MethodInvocation inv = Mockito.mock(MethodInvocation.class);
		when(inv.getMethod()).thenReturn(getOnMessageMethod());
		//
		final Object[] ret = new Object[1];
		ret[0] = Mockito.mock(ConsumerRecord.class);
		when(inv.getArguments()).thenReturn(ret);
		//
		final Tracing tracing = Tracing.newBuilder().build();
		final Tracer localTracer = tracing.tracer();
		when(kafkaTracing.nextSpan(any())).thenReturn(localTracer.nextSpan());
		srv.invoke(inv);
		assertTrue(true);
	}

	@Test
	void testBaseRecord02Error01() throws Throwable {
		final MessageListenerMethodInterceptor srv = new MessageListenerMethodInterceptor<>(kafkaTracing, tracer);
		final MethodInvocation inv = Mockito.mock(MethodInvocation.class);
		when(inv.getMethod()).thenReturn(getOnMessageMethod());
		//
		final Object[] ret = new Object[1];
		ret[0] = Mockito.mock(ConsumerRecord.class);
		when(inv.getArguments()).thenReturn(ret);
		//
		final Tracing tracing = Tracing.newBuilder().build();
		final Tracer localTracer = tracing.tracer();
		when(kafkaTracing.nextSpan(any())).thenReturn(localTracer.nextSpan());
		when(inv.proceed()).thenThrow(new RuntimeException());
		assertThrows(RuntimeException.class, () -> srv.invoke(inv));
		assertTrue(true);
	}

	@Test
	void testBaseRecord02Error02() throws Throwable {
		final MessageListenerMethodInterceptor srv = new MessageListenerMethodInterceptor<>(kafkaTracing, tracer);
		final MethodInvocation inv = Mockito.mock(MethodInvocation.class);
		when(inv.getMethod()).thenReturn(getOnMessageMethod());
		//
		final Object[] ret = new Object[1];
		ret[0] = Mockito.mock(ConsumerRecord.class);
		when(inv.getArguments()).thenReturn(ret);
		//
		final Tracing tracing = Tracing.newBuilder().build();
		final Tracer localTracer = tracing.tracer();
		when(kafkaTracing.nextSpan(any())).thenReturn(localTracer.nextSpan());
		when(inv.proceed()).thenThrow(new RuntimeException("message"));
		assertThrows(RuntimeException.class, () -> srv.invoke(inv));
		assertTrue(true);
	}

	Method getOnMessageMethod() {
		Arrays.stream(this.getClass().getDeclaredMethods()).forEach(x -> System.out.println("" + x.getName()));
		return Arrays.stream(this.getClass().getDeclaredMethods()).filter(x -> "onMessage".equals(x.getName())).findFirst().orElseThrow();
	}

	Method getAnyOtherMethod() {
		return Arrays.stream(this.getClass().getDeclaredMethods()).filter(x -> !"onMessage".equals(x.getName())).findFirst().orElseThrow();
	}

	void onMessage() {
		//
	}
}
