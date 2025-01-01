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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import jakarta.annotation.Nonnull;

public class ActionMessage {
	@Nonnull
	private ActionType actionType = ActionType.UNKNOW;
	@Nonnull
	private UUID objectId;
	@Nonnull
	private Map<String, Object> parameters = new HashMap<>();
	@Nonnull
	private String tenantId;

	public ActionMessage() {
		objectId = UUID.randomUUID();
	}

	public ActionMessage(final ActionType actionType, final UUID objectId, final String tenantId, final Map<String, Object> parameters) {
		this.actionType = actionType;
		this.objectId = objectId;
		this.parameters = parameters;
		this.tenantId = tenantId;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(final ActionType actionType) {
		this.actionType = actionType;
	}

	public UUID getObjectId() {
		return objectId;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setObjectId(final UUID objectId) {
		this.objectId = objectId;
	}

	public void setParameters(final Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(final String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String toString() {
		return "ActionMessage [actionType=" + actionType + ", objectId=" + objectId + ", parameters=" + parameters + ", tenantId=" + tenantId + "]";
	}

}
