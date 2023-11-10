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

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.kafka.core.ConsumerPostProcessor;
import org.springframework.util.ClassUtils;

import brave.kafka.clients.KafkaTracing;

public class TraceConsumerPostProcessor<K, V> implements ConsumerPostProcessor<K, V> {
	private KafkaTracing kafkaTracing;
	private final BeanFactory beanFactory;
	private static final Class tracingConsumer = ClassUtils.resolveClassName("brave.kafka.clients.TracingConsumer", null);

	public TraceConsumerPostProcessor(final BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public Consumer<K, V> apply(final Consumer<K, V> kvConsumer) {
		if (tracingConsumer.isAssignableFrom(ClassUtils.getUserClass(kvConsumer.getClass()))) {
			return kvConsumer;
		}
		return wrapInTracing(kvConsumer);
	}

	private KafkaTracing kafkaTracing() {
		if (this.kafkaTracing == null) {
			this.kafkaTracing = this.beanFactory.getBean(KafkaTracing.class);
		}
		return this.kafkaTracing;
	}

	Consumer<K, V> wrapInTracing(final Consumer<K, V> kvConsumer) {
		return kafkaTracing().consumer(kvConsumer);
	}

}
