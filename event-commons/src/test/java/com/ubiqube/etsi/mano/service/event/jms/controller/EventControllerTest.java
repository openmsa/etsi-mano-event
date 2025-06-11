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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.ubiqube.etsi.mano.service.event.EventManager;

class EventControllerTest {

	@Mock
	private EventManager eventManager;

	@InjectMocks
	private EventController eventController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testNotification() {
		EventMessageDto eventMessageDto = new EventMessageDto();
		// ...set properties on eventMessageDto...

		ResponseEntity<Void> response = eventController.notification(eventMessageDto);
		assertNotNull(response);
		verify(eventManager, times(1)).sendNotification(
				eventMessageDto.getNotificationEvent(),
				eventMessageDto.getObjectId(),
				eventMessageDto.getAdditionalParameters());
	}

	@Test
	void testNfvoAction() {
		ActionMessageDto actionMessageDto = new ActionMessageDto();
		// ...set properties on actionMessageDto...

		ResponseEntity<Void> response = eventController.nfvoAction(actionMessageDto);
		assertNotNull(response);
		verify(eventManager, times(1)).sendActionNfvo(
				actionMessageDto.getActionType(),
				actionMessageDto.getObjectId(),
				actionMessageDto.getParameters());
	}

	@Test
	void testVnfmAction() {
		ActionMessageDto actionMessageDto = new ActionMessageDto();
		// ...set properties on actionMessageDto...

		ResponseEntity<Void> response = eventController.vnfmAction(actionMessageDto);
		assertNotNull(response);
		verify(eventManager, times(1)).sendActionVnfm(
				actionMessageDto.getActionType(),
				actionMessageDto.getObjectId(),
				actionMessageDto.getParameters());
	}
}
