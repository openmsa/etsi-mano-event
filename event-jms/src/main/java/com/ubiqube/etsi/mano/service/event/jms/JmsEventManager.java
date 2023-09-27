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
package com.ubiqube.etsi.mano.service.event.jms;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.auth.config.TenantHolder;
import com.ubiqube.etsi.mano.service.event.ActionMessage;
import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.SubscriptionEvent;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

import jakarta.annotation.Nullable;

@Service
public class JmsEventManager implements EventManager {

	private static final Logger LOG = LoggerFactory.getLogger(JmsEventManager.class);

	private final JmsTemplate jmsTemplate;
	private final EventMessageJpa eventMessageJpa;
	private final ConfigurableApplicationContext configurableApplicationContext;

	public JmsEventManager(final JmsTemplate jmsTemplate, final EventMessageJpa eventMessageJpa, final ConfigurableApplicationContext configurableApplicationContext) {
		this.jmsTemplate = jmsTemplate;
		this.eventMessageJpa = eventMessageJpa;
		this.configurableApplicationContext = configurableApplicationContext;
	}

	@Override
	public void sendNotification(final @Nullable NotificationEvent notificationEvent, final @Nullable UUID objectId, final @Nullable Map<String, String> additionalParameters) {
		EventMessage msg = new EventMessage(notificationEvent, objectId, additionalParameters);
		msg = eventMessageJpa.save(msg);
		LOG.info("Sending notification {}", msg);
		jmsTemplate.convertAndSend(resolvQueueName(Constants.QUEUE_NOTIFICATION), msg);
	}

	@Override
	public void sendActionVnfm(final @Nullable ActionType actionType, final @Nullable UUID objectId, final @Nullable Map<String, Object> parameters) {
		sendAction(Constants.QUEUE_VNFM_ACTIONS, actionType, objectId, parameters);
	}

	@Override
	public void sendActionNfvo(final @Nullable ActionType actionType, final @Nullable UUID objectId, final @Nullable Map<String, Object> parameters) {
		sendAction(Constants.QUEUE_NFVO_ACTIONS, actionType, objectId, parameters);
	}

	@Override
	public void sendGrant(final @Nullable UUID objectId, final @Nullable Map<String, Object> parameters) {
		final String tenant = Objects.requireNonNull(TenantHolder.getTenantId());
		final GrantMessage msg = new GrantMessage(objectId, tenant, parameters);
		jmsTemplate.convertAndSend(resolvQueueName(Constants.QUEUE_GRANT), msg);
	}

	@Override
	public void sendAction(final ActionType actionType, final UUID objectId) {
		sendAction(Constants.QUEUE_COMMON_ACTION, actionType, objectId, Map.of());
	}

	private void sendAction(final String queueName, @Nullable final ActionType actionType, @Nullable final UUID objectId, final Map<String, Object> parameters) {
		final String tenant = Objects.requireNonNull(TenantHolder.getTenantId());
		final ActionMessage msg = new ActionMessage(actionType, objectId, tenant, parameters);
		jmsTemplate.convertAndSend(resolvQueueName(queueName), msg);
	}

	@Override
	public void notificationSender(final @Nullable SubscriptionEvent se) {
		jmsTemplate.convertAndSend(resolvQueueName(Constants.QUEUE_NOTIFICATION_SENDER), se);
	}

	private String resolvQueueName(final String queueName) {
		final ConfigurableListableBeanFactory configurableListableBeanFactory = configurableApplicationContext.getBeanFactory();
		final String ret = configurableListableBeanFactory.resolveEmbeddedValue(queueName);
		Objects.requireNonNull(ret);
		return ret;
	}
}
