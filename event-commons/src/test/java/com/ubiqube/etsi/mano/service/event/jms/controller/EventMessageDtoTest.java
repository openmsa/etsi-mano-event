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
package com.ubiqube.etsi.mano.service.event.jms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

class EventMessageDtoTest {

	private EventMessageDto eventMessageDto;
	private UUID id;
	private NotificationEvent notificationEvent;
	private UUID objectId;
	private Map<String, String> additionalParameters;

	@BeforeEach
	void setUp() {
		id = UUID.randomUUID();
		notificationEvent = NotificationEvent.APP_INSTANCE_CREATE;
		objectId = UUID.randomUUID();
		additionalParameters = new HashMap<>();
		additionalParameters.put("key1", "value1");

		eventMessageDto = new EventMessageDto();
		eventMessageDto.setId(id);
		eventMessageDto.setNotificationEvent(notificationEvent);
		eventMessageDto.setObjectId(objectId);
		eventMessageDto.setAdditionalParameters(additionalParameters);
	}

	@Test
	void testEventMessageDto() {
		assertNotNull(eventMessageDto);
		assertEquals(id, eventMessageDto.getId());
		assertEquals(notificationEvent, eventMessageDto.getNotificationEvent());
		assertEquals(objectId, eventMessageDto.getObjectId());
		assertEquals(additionalParameters, eventMessageDto.getAdditionalParameters());
	}
}
