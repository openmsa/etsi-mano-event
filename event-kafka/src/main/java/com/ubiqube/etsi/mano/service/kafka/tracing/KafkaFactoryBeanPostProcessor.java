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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;

public class KafkaFactoryBeanPostProcessor implements BeanPostProcessor {

	private final BeanFactory beanFactory;

	public KafkaFactoryBeanPostProcessor(final BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		// Don't use generics on factory,it pose problems on openJDK.
		if (bean instanceof final ConsumerFactory factory) {
			if (factory.getPostProcessors().stream().noneMatch(TraceConsumerPostProcessor.class::isInstance)) {
				factory.addPostProcessor(new TraceConsumerPostProcessor<>(this.beanFactory));
			}
		} else if ((bean instanceof final ProducerFactory factory) && factory.getPostProcessors().stream().noneMatch(TraceProducerPostProcessor.class::isInstance)) {
			factory.addPostProcessor(new TraceProducerPostProcessor<>(this.beanFactory));
		}
		return bean;
	}
}
