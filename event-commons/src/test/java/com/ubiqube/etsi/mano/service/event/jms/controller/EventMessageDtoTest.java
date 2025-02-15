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
	public void setUp() {
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
