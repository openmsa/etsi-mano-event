package com.ubiqube.etsi.mano.service.event.jms.controller;

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
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testNotification() {
		EventMessageDto eventMessageDto = new EventMessageDto();
		// ...set properties on eventMessageDto...

		ResponseEntity<Void> response = eventController.notification(eventMessageDto);

		verify(eventManager, times(1)).sendNotification(
				eventMessageDto.getNotificationEvent(),
				eventMessageDto.getObjectId(),
				eventMessageDto.getAdditionalParameters());
	}

	@Test
	public void testNfvoAction() {
		ActionMessageDto actionMessageDto = new ActionMessageDto();
		// ...set properties on actionMessageDto...

		ResponseEntity<Void> response = eventController.nfvoAction(actionMessageDto);

		verify(eventManager, times(1)).sendActionNfvo(
				actionMessageDto.getActionType(),
				actionMessageDto.getObjectId(),
				actionMessageDto.getParameters());
	}

	@Test
	public void testVnfmAction() {
		ActionMessageDto actionMessageDto = new ActionMessageDto();
		// ...set properties on actionMessageDto...

		ResponseEntity<Void> response = eventController.vnfmAction(actionMessageDto);

		verify(eventManager, times(1)).sendActionVnfm(
				actionMessageDto.getActionType(),
				actionMessageDto.getObjectId(),
				actionMessageDto.getParameters());
	}
}
