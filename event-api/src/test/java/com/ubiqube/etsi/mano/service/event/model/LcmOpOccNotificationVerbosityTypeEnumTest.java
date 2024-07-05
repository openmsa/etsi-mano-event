package com.ubiqube.etsi.mano.service.event.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class LcmOpOccNotificationVerbosityTypeEnumTest {

	@Test
	void test() {
		LcmOpOccNotificationVerbosityTypeEnum e = LcmOpOccNotificationVerbosityTypeEnum.fromValue(null);
		assertNull(e);
		e = LcmOpOccNotificationVerbosityTypeEnum.fromValue("FULL");
		assertNotNull(e);
		assertNotNull(e.toString());
	}

}
