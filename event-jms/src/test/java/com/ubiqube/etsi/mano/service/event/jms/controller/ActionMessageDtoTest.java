package com.ubiqube.etsi.mano.service.event.jms.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.event.ActionType;

class ActionMessageDtoTest {

	@Test
	void test() {
		ActionMessageDto srv = new ActionMessageDto();
		srv = new ActionMessageDto(ActionType.GRANT_REQUEST, UUID.randomUUID(), Map.of(), "tenant");
		assertEquals(ActionType.GRANT_REQUEST, srv.getActionType());
		assertNotNull(srv.getObjectId());
		assertEquals(Map.of(), srv.getParameters());
		assertEquals("tenant", srv.getTenantId());
		srv.setActionType(null);
		srv.setObjectId(null);
		srv.setParameters(null);
		srv.setTenantId(null);
	}

}
