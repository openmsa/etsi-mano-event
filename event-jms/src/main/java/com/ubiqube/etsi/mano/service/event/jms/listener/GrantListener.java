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
package com.ubiqube.etsi.mano.service.event.jms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.ubiqube.etsi.mano.auth.config.TenantHolder;
import com.ubiqube.etsi.mano.service.event.GrantActionDispatcher;
import com.ubiqube.etsi.mano.service.event.jms.Constants;
import com.ubiqube.etsi.mano.service.event.jms.GrantMessage;

import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@Service
@Transactional(TxType.NEVER)
public class GrantListener {
	private final GrantActionDispatcher grantActionDispatcher;

	public GrantListener(final GrantActionDispatcher grantActionDispatcher) {
		this.grantActionDispatcher = grantActionDispatcher;
	}

	@JmsListener(destination = Constants.QUEUE_GRANT, concurrency = "5-10")
	@Transactional(TxType.NEVER)
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NEVER)
	public void onEvent(final GrantMessage ev) {
		TenantHolder.setTenantId(ev.getTenantId());
		grantActionDispatcher.dispatch(ev.getObjectId());
	}
}
