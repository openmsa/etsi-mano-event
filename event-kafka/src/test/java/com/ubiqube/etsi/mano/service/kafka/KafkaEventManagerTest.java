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
package com.ubiqube.etsi.mano.service.kafka;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

import com.ubiqube.etsi.mano.auth.config.TenantHolder;
import com.ubiqube.etsi.mano.service.event.SubscriptionEvent;
import com.ubiqube.etsi.mano.service.event.jms.EventMessageJpa;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;

@ExtendWith(MockitoExtension.class)
class KafkaEventManagerTest {
	@Mock
	private KafkaTemplate<String, EventMessage> kt;
	@Mock
	private ConfigurableApplicationContext cac;
	@Mock
	private EventMessageJpa eventMessageJpa;
	@Mock
	private ConfigurableListableBeanFactory clbf;

	@BeforeEach
	void setup() {
		TenantHolder.setTenantId("test");
	}

	@Test
	void testNotification() {
		final KafkaEventManager srv = new KafkaEventManager(kt, cac, eventMessageJpa);
		configureCac();
		srv.notificationSender(new SubscriptionEvent());
		assertTrue(true);
	}

	@Test
	void testActionNfvo() {
		final KafkaEventManager srv = new KafkaEventManager(kt, cac, eventMessageJpa);
		configureCac();
		srv.sendActionNfvo(null, null, null);
		assertTrue(true);
	}

	@Test
	void testActionVnfm() {
		final KafkaEventManager srv = new KafkaEventManager(kt, cac, eventMessageJpa);
		configureCac();
		srv.sendActionVnfm(null, null, null);
		assertTrue(true);
	}

	@Test
	void testAction() {
		final KafkaEventManager srv = new KafkaEventManager(kt, cac, eventMessageJpa);
		configureCac();
		srv.sendAction(null, null);
		assertTrue(true);
	}

	@Test
	void testGrant() {
		configureCac();
		final KafkaEventManager srv = new KafkaEventManager(kt, cac, eventMessageJpa);
		srv.sendGrant(null, null);
		assertTrue(true);
	}

	void configureCac() {
		when(cac.getBeanFactory()).thenReturn(clbf);
		when(clbf.resolveEmbeddedValue(anyString())).thenReturn("test-queue");
	}
}
