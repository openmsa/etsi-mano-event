/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
package com.ubiqube.etsi.mano.service.event.jms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubiqube.etsi.mano.service.event.EventManager;

import jakarta.annotation.security.RolesAllowed;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@RestController
@RolesAllowed({ "ROLE_ADMIN" })
@RequestMapping("/admin/event")
public class EventController {

	private static final Logger LOG = LoggerFactory.getLogger(EventController.class);

	private final EventManager eventManager;

	public EventController(final EventManager eventManager) {
		this.eventManager = eventManager;
	}

	@PostMapping(value = "/notification", consumes = { "application/json" })
	public ResponseEntity<Void> notification(@RequestBody final EventMessageDto ev) {
		eventManager.sendNotification(ev.getNotificationEvent(), ev.getObjectId(), ev.getAdditionalParameters());
		return tailReturn();
	}

	@PostMapping(value = "/action/nfvo", consumes = { "application/json" })
	public ResponseEntity<Void> nfvoAction(@RequestBody final ActionMessageDto ev) {
		eventManager.sendActionNfvo(ev.getActionType(), ev.getObjectId(), ev.getParameters());
		return tailReturn();
	}

	@PostMapping(value = "/action/vnfm", consumes = { "application/json" })
	public ResponseEntity<Void> vnfmAction(@RequestBody final ActionMessageDto ev) {
		eventManager.sendActionVnfm(ev.getActionType(), ev.getObjectId(), ev.getParameters());
		return tailReturn();
	}

	private static ResponseEntity<Void> tailReturn() {
		LOG.info("Notification sent.");
		return ResponseEntity.noContent().build();
	}
}
