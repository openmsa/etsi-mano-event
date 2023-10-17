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

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ubiqube.etsi.mano.auth.config.TenantHolder;
import com.ubiqube.etsi.mano.service.event.GrantActionDispatcher;
import com.ubiqube.etsi.mano.service.event.jms.GrantMessage;
import com.ubiqube.etsi.mano.service.kafka.Constants;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
@Transactional(TxType.NEVER)
public class KafkaGrantListener {
	private final GrantActionDispatcher grantActionDispatcher;

	public KafkaGrantListener(final GrantActionDispatcher grantActionDispatcher) {
		this.grantActionDispatcher = grantActionDispatcher;
	}

	@KafkaListener(topics = Constants.QUEUE_GRANT, groupId = "mano", concurrency = "5")
	@Transactional(TxType.NEVER)
	public void onEvent(final GrantMessage ev) {
		TenantHolder.setTenantId(ev.getTenantId());
		grantActionDispatcher.dispatch(ev.getObjectId());
	}

}
