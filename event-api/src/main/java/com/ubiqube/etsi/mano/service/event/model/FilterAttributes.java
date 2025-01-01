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

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ncuser
 *
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class FilterAttributes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private String attribute;

	private String value;

	public FilterAttributes(final String attr, final String value) {
		this.attribute = attr;
		this.value = value;
	}

	public static FilterAttributes of(final String attr, final String value) {
		return new FilterAttributes(attr, value);
	}

	@Override
	public String toString() {
		return "FilterAttributes [attribute=" + attribute + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(attribute, value);
	}

	/**
	 * Don't use `id` on comparison.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		final FilterAttributes other = (FilterAttributes) obj;
		return Objects.equals(attribute, other.attribute) && Objects.equals(value, other.value);
	}

}
