package com.ubiqube.etsi.mano.service.event.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class FilterAttributesTest {

	@Test
	void test() {
		final FilterAttributes srv = new FilterAttributes("attr", "val");
		assertNotNull(srv.toString());
	}

}
