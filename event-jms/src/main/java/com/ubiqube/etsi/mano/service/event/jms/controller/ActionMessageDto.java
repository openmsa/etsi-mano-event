package com.ubiqube.etsi.mano.service.event.jms.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ubiqube.etsi.mano.service.event.ActionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActionMessageDto {
	private ActionType actionType = ActionType.UNKNOW;
	private UUID objectId;
	private Map<String, Object> parameters = new HashMap<>();
	private String tenantId;
}
