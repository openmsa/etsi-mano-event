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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;

import org.junit.jupiter.api.Test;

import com.ubiqube.etsi.mano.service.auth.model.AuthParamOauth2;
import com.ubiqube.etsi.mano.service.auth.model.OAuth2GrantType;

@SuppressWarnings("static-method")
class AuthParamOauth2Test {

	@Test
	void testBuilder() {
		final AuthParamOauth2 res = AuthParamOauth2.builder()
				.clientId("")
				.clientSecret("")
				.grantType(OAuth2GrantType.CLIENT_CREDENTIAL)
				.o2AuthTlsCert("")
				.o2IgnoreSsl(true)
				.o2Password("")
				.o2Username("")
				.tokenEndpoint(URI.create("http://localhost/"))
				.build();
		assertNotNull(res);
		assertNotNull(res.toString());
	}

	@Test
	void testbuilderToString() {
		final String res = AuthParamOauth2.builder()
				.clientId("")
				.clientSecret("")
				.grantType(OAuth2GrantType.fromValue("CLIENT_CREDENTIAL"))
				.o2AuthTlsCert("")
				.o2IgnoreSsl(true)
				.o2Password("")
				.o2Username("")
				.tokenEndpoint(URI.create("http://localhost/"))
				.toString();
		assertNotNull(res);
	}

	@Test
	void testCtor() {
		final AuthParamOauth2 apo = new AuthParamOauth2();
		apo.setClientId("");
		apo.setClientSecret("");
		apo.setGrantType(OAuth2GrantType.PASSWORD);
		apo.setO2AuthTlsCert("");
		apo.setO2IgnoreSsl(false);
		apo.setO2Password("");
		apo.setO2Username("");
		apo.setTokenEndpoint(URI.create("http://localhost/"));
		assertNotNull(apo.getClientId());
		assertNotNull(apo.getClientSecret());
		assertNotNull(apo.getGrantType());
		assertNotNull(apo.getO2AuthTlsCert());
		assertNotNull(apo.getO2IgnoreSsl());
		assertNotNull(apo.getO2Password());
		assertNotNull(apo.getO2Username());
		assertNotNull(apo.getTokenEndpoint());
	}
}
