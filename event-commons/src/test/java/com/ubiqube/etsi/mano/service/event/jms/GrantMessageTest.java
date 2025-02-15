package com.ubiqube.etsi.mano.service.event.jms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class GrantMessageTest {

	@Test
	void testDefaultConstructor() {
		GrantMessage message = new GrantMessage();
		assertNotNull(message.getObjectId());
		assertEquals("BOOTSTRAP", message.getTenantId());
		assertNotNull(message.getParameters());
		assertTrue(message.getParameters().isEmpty());
	}

	@Test
	void testParameterizedConstructor() {
		UUID objectId = UUID.randomUUID();
		String tenantId = "tenant123";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("key1", "value1");

		GrantMessage message = new GrantMessage(objectId, tenantId, parameters);

		assertEquals(objectId, message.getObjectId());
		assertEquals(tenantId, message.getTenantId());
		assertEquals(parameters, message.getParameters());
	}

	@Test
	void testSettersAndGetters() {
		GrantMessage message = new GrantMessage();
		UUID objectId = UUID.randomUUID();
		String tenantId = "tenant123";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("key1", "value1");

		message.setObjectId(objectId);
		message.setTenantId(tenantId);
		message.setParameters(parameters);

		assertEquals(objectId, message.getObjectId());
		assertEquals(tenantId, message.getTenantId());
		assertEquals(parameters, message.getParameters());
	}
}
