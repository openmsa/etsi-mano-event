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
package com.ubiqube.etsi.mano.service.event.quartz;

import java.util.Map;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.service.event.ActionType;
import com.ubiqube.etsi.mano.service.event.EventManager;
import com.ubiqube.etsi.mano.service.event.SubscriptionEvent;
import com.ubiqube.etsi.mano.service.event.model.EventMessage;
import com.ubiqube.etsi.mano.service.event.model.NotificationEvent;

/**
 * Simple implementation using Quartz.
 *
 * @author Olivier Vignaud {@literal <ovi@ubiqube.com>}
 *
 */
@Service
public class QuartzEventManager implements EventManager {
	/** Logger instance. */
	private static final Logger LOG = LoggerFactory.getLogger(QuartzEventManager.class);
	/** Scheduler instance injection point. */
	private final Scheduler scheduler;
	private final EventMessageJpa eventMessageJpa;

	public QuartzEventManager(final Scheduler scheduler, final EventMessageJpa eventMessageJpa) {
		this.scheduler = scheduler;
		this.eventMessageJpa = eventMessageJpa;
		try {
			this.scheduler.getListenerManager().addJobListener(new UriUploadListener());
		} catch (final SchedulerException e) {
			throw new QuartzSchedulerException(e);
		}
	}

	@Override
	public void sendNotification(final NotificationEvent notificationEvent, final UUID objectId, final Map<String, String> additionalParameters) {
		LOG.info("Starting notification : {}/{}", notificationEvent, objectId);
		EventMessage msg = new EventMessage(notificationEvent, objectId, additionalParameters);
		msg = eventMessageJpa.save(msg);
		createJob(msg.getId(), additionalParameters, notificationEvent.value(), objectId, NotificationJob.class);
	}

	@Override
	public void sendActionVnfm(final ActionType actionType, final UUID objectId, final Map<String, Object> parameters) {
		LOG.info("Starting sendAction VNFM : {}/{}", actionType, objectId);
		createJob(parameters, actionType.value(), objectId, ActionJob.class);
	}

	@Override
	public void sendGrant(final UUID objectId, final Map<String, Object> parameters) {
		LOG.info("Starting send Grant : Grant/{}", objectId);
		createJob(parameters, ActionType.GRANT_REQUEST.value(), objectId, GrantJob.class);
	}

	@Override
	public void sendActionNfvo(final ActionType actionType, final UUID objectId, final Map<String, Object> parameters) {
		LOG.info("Starting sendAction NFVO : {}/{}", actionType, objectId);
		createJob(parameters, actionType.value(), objectId, ActionNfvoJob.class);
	}

	@Override
	public void sendAction(final ActionType actionType, final UUID objectId) {
		LOG.info("Starting sendAction : {}/{}", actionType, objectId);
		createJob(Map.of(), actionType.value(), objectId, ActionJob.class);

	}

	private <U> void createJob(final Map<String, U> parameters, final String actionType, final UUID objectId, final Class<? extends QuartzJobBean> controllers) {
		createJob(UUID.randomUUID(), parameters, actionType, objectId, controllers);
	}

	private <U> void createJob(final UUID id, final Map<String, U> parameters, final String actionType, final UUID objectId, final Class<? extends QuartzJobBean> controllers) {
		final JobDataMap jobDataMap = QuartzEventUtils.createJobMap(id, actionType, objectId, parameters);
		launchJob(actionType, controllers, jobDataMap);
	}

	private void launchJob(final String actionType, final Class<? extends QuartzJobBean> controllers, final JobDataMap jobDataMap) {
		final JobDetail jobDetail = JobBuilder.newJob(controllers)
				.withIdentity(UUID.randomUUID().toString(), actionType + "-jobs")
				.withDescription("Action ETSI-MANO")
				.usingJobData(jobDataMap)
				.build();

		final Trigger trigger = TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), actionType + "-triggers")
				.withDescription("Send Action")
				.build();
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (final SchedulerException e) {
			throw new QuartzSchedulerException(e);
		}
	}

	@Override
	public void notificationSender(final SubscriptionEvent se) {
		final JobDataMap jobDataMap = QuartzEventUtils.createSubscriptionEvent(se);
		launchJob("subscription-send", SendSubscriptionJob.class, jobDataMap);
	}
}
