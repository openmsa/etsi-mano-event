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

import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.kafka.core.ProducerPostProcessor;
import org.springframework.util.ClassUtils;

import brave.kafka.clients.KafkaTracing;

public class TraceProducerPostProcessor<K, V> implements ProducerPostProcessor<K, V> {

	private final BeanFactory beanFactory;

	private KafkaTracing kafkaTracing;

	// Because it's not public in Brave
	private static final Class tracingProducer = ClassUtils.resolveClassName("brave.kafka.clients.TracingProducer",
			null);

	public TraceProducerPostProcessor(final BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	private KafkaTracing kafkaTracing() {
		if (this.kafkaTracing == null) {
			this.kafkaTracing = this.beanFactory.getBean(KafkaTracing.class);
		}
		return this.kafkaTracing;
	}

	@Override
	public Producer<K, V> apply(final Producer<K, V> kvProducer) {
		if (tracingProducer.isAssignableFrom(ClassUtils.getUserClass(kvProducer.getClass()))) {
			return kvProducer;
		}
		return wrapInTracing(kvProducer);
	}

	Producer<K, V> wrapInTracing(final Producer<K, V> kvProducer) {
		return kafkaTracing().producer(kvProducer);
	}

}
