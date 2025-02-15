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
