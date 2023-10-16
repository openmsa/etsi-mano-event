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
package com.ubiqube.etsi.mano.service.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@SuppressWarnings("static-method")
public class KafkaConfig {

	@Bean
	StringJsonMessageConverter stringMessageConverter(final ObjectMapper objectMapper) {
		return new StringJsonMessageConverter(objectMapper);
	}

	@Bean
	public KafkaListenerContainerFactory<?> kafkaJsonListenerContainerFactory(final StringJsonMessageConverter jsonMessageConverter,
			final ConsumerFactory<String, String> defaultKafkaConsumerFactory) {
		final ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(defaultKafkaConsumerFactory);
		factory.setRecordMessageConverter(jsonMessageConverter);
		return factory;
	}
}
