/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.service.event.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class FilterAttributesTest {

	@Test
	void test() {
		final FilterAttributes srv = new FilterAttributes("attr", "val");
		assertNotNull(srv.toString());
	}

	@Test
	void testFilterAttributes() {
		// Id should not be used in comparison.
		final FilterAttributes fa1 = new FilterAttributes("attr", "value");
		final FilterAttributes fa2 = new FilterAttributes("attr", "value");
		assertEquals(fa1, fa2);
		fa1.setId(UUID.randomUUID());
		assertEquals(fa1, fa2);
		fa2.setAttribute("other");
		assertNotEquals(fa1, fa2);
	}
}
