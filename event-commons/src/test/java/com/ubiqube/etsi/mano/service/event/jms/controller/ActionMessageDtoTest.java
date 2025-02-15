package com.ubiqube.etsi.mano.service.event.jms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.event.ActionType;

class ActionMessageDtoTest {

	@Test
	void testActionMessageDto() {
		UUID objectId = UUID.randomUUID();
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("key", "value");
		String tenantId = "tenant123";

		ActionMessageDto dto = new ActionMessageDto(ActionType.GRANT_REQUEST, objectId, parameters, tenantId);

		assertEquals(ActionType.GRANT_REQUEST, dto.getActionType());
		assertEquals(objectId, dto.getObjectId());
		assertEquals(parameters, dto.getParameters());
		assertEquals(tenantId, dto.getTenantId());
	}

	@Test
	void testNoArgsConstructor() {
		ActionMessageDto dto = new ActionMessageDto();

		assertNotNull(dto);
		assertEquals(ActionType.UNKNOW, dto.getActionType());
		assertNotNull(dto.getParameters());
	}
}
