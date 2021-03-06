/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.handlingunits.reservation;

import de.metas.common.util.CoalesceUtil;
import de.metas.order.OrderLineId;
import de.metas.project.ProjectAndLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
public class HUReservationDocRef
{
	@Nullable
	OrderLineId salesOrderLineId;
	@Nullable
	ProjectAndLineId projectAndLineId;

	@Builder
	private HUReservationDocRef(
			@Nullable final OrderLineId salesOrderLineId,
			@Nullable final ProjectAndLineId projectAndLineId)
	{
		if (CoalesceUtil.countNotNulls(salesOrderLineId, projectAndLineId) != 1)
		{
			throw new AdempiereException("One and only one document shall be set")
					.appendParametersToMessage()
					.setParameter("salesOrderLineId", salesOrderLineId)
					.setParameter("projectAndLineId", projectAndLineId);
		}

		this.salesOrderLineId = salesOrderLineId;
		this.projectAndLineId = projectAndLineId;
	}

	public static HUReservationDocRef ofSalesOrderLineId(@NonNull final OrderLineId salesOrderLineId)
	{
		return HUReservationDocRef.builder().salesOrderLineId(salesOrderLineId).build();
	}

	public static HUReservationDocRef ofProjectAndLineId(@NonNull final ProjectAndLineId projectAndLineId)
	{
		return HUReservationDocRef.builder().projectAndLineId(projectAndLineId).build();
	}

}
