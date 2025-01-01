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

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.event.ActionType;

@SuppressWarnings("static-method")
class ActionMessageDtoTest {

	@Test
	void test() {
		ActionMessageDto srv = new ActionMessageDto();
		assertNotNull(srv);
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
