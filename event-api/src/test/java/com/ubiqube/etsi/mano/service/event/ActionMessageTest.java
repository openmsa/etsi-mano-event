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
package com.ubiqube.etsi.mano.service.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class ActionMessageTest {

	@Test
	void testCtor001() {
		final ActionMessage message = new ActionMessage();
		message.setActionType(ActionType.GRANT_REQUEST);
		message.setObjectId(UUID.randomUUID());
		message.setParameters(Map.of());
		message.setTenantId("tenant");
		message.toString();
		assertTrue(true);
	}

	@Test
	void testCtor002() {
		final UUID uuid = UUID.randomUUID();
		final ActionMessage message = new ActionMessage(ActionType.GRANT_REQUEST, uuid, "tenant", Map.of());
		assertEquals(ActionType.GRANT_REQUEST, message.getActionType());
		assertEquals(uuid, message.getObjectId());
		assertEquals(Map.of(), message.getParameters());
		assertEquals("tenant", message.getTenantId());
	}
}
