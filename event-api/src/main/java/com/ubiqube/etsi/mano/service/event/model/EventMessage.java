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
package com.ubiqube.etsi.mano.service.event.model;

import java.util.Map;
import java.util.UUID;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Getter
@Setter
@Entity
public class EventMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Enumerated(EnumType.STRING)
	private NotificationEvent notificationEvent;
	private UUID objectId;
	@ElementCollection
	private Map<String, String> additionalParameters;

	public EventMessage() {
		// Nothing.
	}

	public EventMessage(final NotificationEvent notificationEvent, final UUID objectId, final Map<String, String> additionalParameters) {
		this.notificationEvent = notificationEvent;
		this.objectId = objectId;
		this.additionalParameters = additionalParameters;
	}

	@Override
	public String toString() {
		return "EventMessage [notificationEvent=" + notificationEvent + ", objectId=" + objectId + "]";
	}

}
