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

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.MessageListenerContainer;

import brave.Tracer;
import brave.kafka.clients.KafkaTracing;

@Aspect
public class SleuthKafkaAspect {

	private static final Logger log = LoggerFactory.getLogger(SleuthKafkaAspect.class);

	private final KafkaTracing kafkaTracing;

	private final Tracer tracer;

	public SleuthKafkaAspect(final KafkaTracing kafkaTracing, final Tracer tracer) {
		this.kafkaTracing = kafkaTracing;
		this.tracer = tracer;
	}

	@Pointcut("execution(public * org.springframework.kafka.config.KafkaListenerContainerFactory.createListenerContainer(..))")
	private void anyCreateListenerContainer() {
	} // NOSONAR

	@Pointcut("execution(public * org.springframework.kafka.config.KafkaListenerContainerFactory.createContainer(..))")
	private void anyCreateContainer() {
	} // NOSONAR

	@Around("anyCreateListenerContainer() || anyCreateContainer()")
	public Object wrapListenerContainerCreation(final ProceedingJoinPoint pjp) throws Throwable {
		final MessageListenerContainer listener = (MessageListenerContainer) pjp.proceed();
		if (listener instanceof final AbstractMessageListenerContainer<?, ?> container) {
			final Object someMessageListener = container.getContainerProperties().getMessageListener();
			if (someMessageListener instanceof MessageListener) {
				container.setupMessageListener(createProxy(someMessageListener));
			} else {
				log.debug("ATM we don't support Batch message listeners");
			}
		} else {
			log.debug("Can't wrap this listener. Proceeding");
		}
		return listener;
	}

	@SuppressWarnings("unchecked")
	Object createProxy(final Object bean) {
		final ProxyFactoryBean factory = new ProxyFactoryBean();
		factory.setProxyTargetClass(true);
		factory.addAdvice(new MessageListenerMethodInterceptor(this.kafkaTracing, this.tracer));
		factory.setTarget(bean);
		return factory.getObject();
	}
}
