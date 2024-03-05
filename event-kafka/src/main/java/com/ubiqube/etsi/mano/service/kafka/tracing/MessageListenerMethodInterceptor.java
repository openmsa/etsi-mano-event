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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import brave.Span;
import brave.Tracer;
import brave.kafka.clients.KafkaTracing;

public class MessageListenerMethodInterceptor<T extends MessageListener> implements MethodInterceptor {

	private static final Log log = LogFactory.getLog(MessageListenerMethodInterceptor.class);

	private final KafkaTracing kafkaTracing;

	private final Tracer tracer;

	public MessageListenerMethodInterceptor(final KafkaTracing kafkaTracing, final Tracer tracer) {
		this.kafkaTracing = kafkaTracing;
		this.tracer = tracer;
	}

	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		if (!"onMessage".equals(invocation.getMethod().getName())) {
			return invocation.proceed();
		}
		final Object[] arguments = invocation.getArguments();
		final Object record = record(arguments);
		if (record == null) {
			return invocation.proceed();
		}
		log.debug("Wrapping onMessage call");
		final Span span = this.kafkaTracing.nextSpan((ConsumerRecord<?, ?>) record).name("on-message").start();
		try (Tracer.SpanInScope ws = this.tracer.withSpanInScope(span)) {
			return invocation.proceed();
		} catch (RuntimeException | Error e) {
			String message = e.getMessage();
			if (message == null) {
				message = e.getClass().getSimpleName();
			}
			span.tag("error", message);
			throw e;
		} finally {
			span.finish();
		}
	}

	private Object record(final Object[] arguments) {
		for (final Object object : arguments) {
			if (object instanceof ConsumerRecord) {
				return object;
			}
		}
		return null;
	}
}
