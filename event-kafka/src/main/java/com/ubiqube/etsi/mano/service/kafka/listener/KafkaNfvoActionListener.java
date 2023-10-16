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
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.service.kafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.service.event.ActionController;
import com.ubiqube.etsi.mano.service.event.ActionMessage;
import com.ubiqube.etsi.mano.service.kafka.Constants;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
public class KafkaNfvoActionListener {
	private static final Logger LOG = LoggerFactory.getLogger(KafkaNfvoActionListener.class);

	private final ActionController actionController;

	public KafkaNfvoActionListener(final ActionController actionController) {
		this.actionController = actionController;
	}

	@KafkaListener(topics = Constants.QUEUE_NFVO_ACTIONS, concurrency = "10")
	@Transactional(TxType.NEVER)
	public void onEvent(final ActionMessage ev) {
		LOG.info("JMS NFVO ActionController Receiving Action: {}", ev);
		actionController.onEvent(ev);
		LOG.info("JMS NFVO ActionController Done for event: {}", ev);
	}

}
