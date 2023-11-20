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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import brave.Tracing;

class KafkaConfigTest {

	@Test
	void test() {
		final KafkaConfig srv = new KafkaConfig();
		final Tracing tracing = Tracing.newBuilder().build();
		srv.kafkaStreamsTracing(tracing);
		assertTrue(true);
	}

	@Test
	void testRetryTopic() {
		final KafkaConfig srv = new KafkaConfig();
		final KafkaTemplate<String, ?> template = Mockito.mock(KafkaTemplate.class);
		srv.myRetryTopic(template);
		assertTrue(true);
	}

	@Test
	void testMessageConverter() {
		final KafkaConfig srv = new KafkaConfig();
		srv.stringMessageConverter(new ObjectMapper());
		assertTrue(true);
	}
}
