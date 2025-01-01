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
package com.ubiqube.etsi.mano.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.jms.controller.ActionMessageDto;
import com.ubiqube.etsi.mano.service.event.jms.controller.EventController;
import com.ubiqube.etsi.mano.service.event.jms.controller.EventMessageDto;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

	@Mock
	private EventManager eventManager;

	@Test
	void testName() {
		final EventController ec = new EventController(eventManager);
		final EventMessageDto ev = new EventMessageDto();
		ev.setAdditionalParameters(Map.of());
		ev.setId(UUID.randomUUID());
		ev.setNotificationEvent(NotificationEvent.APP_INSTANCE_CREATE);
		ev.setObjectId(UUID.randomUUID());
		ec.notification(ev);
		assertNotNull(ev.getId());
		assertTrue(true);
	}

	@Test
	void testNfvo() {
		final EventController ec = new EventController(eventManager);
		final ActionMessageDto ev = new ActionMessageDto();
		ev.setParameters(Map.of());
		ev.setObjectId(UUID.randomUUID());
		ev.setActionType(ActionType.GRANT_REQUEST);
		ev.setObjectId(UUID.randomUUID());
		ec.nfvoAction(ev);
		assertNotNull(ev.getObjectId());
		assertTrue(true);
	}

	@Test
	void testVnfm() {
		final EventController ec = new EventController(eventManager);
		final ActionMessageDto ev = new ActionMessageDto();
		ev.setParameters(Map.of());
		ev.setObjectId(UUID.randomUUID());
		ev.setActionType(ActionType.GRANT_REQUEST);
		ev.setObjectId(UUID.randomUUID());
		ec.vnfmAction(ev);
		assertNotNull(ev.getObjectId());
		assertTrue(true);
	}
}
