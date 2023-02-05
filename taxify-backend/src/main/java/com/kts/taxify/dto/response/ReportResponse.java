package com.kts.taxify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
public class ReportResponse {
    LocalDate initDate;
    LocalDate termDate;
    Collection<GraphNode> numberOfRidesPerDate;
    Double averageNumberOfRidesPerDate;
    Double totalNumberOfRidesForPeriod;
    Collection<GraphNode> numberOfKilometersPerDate;
    Double averageNumberOfKilometersPerDate;
    Double totalNumberOfKilometersForPeriod;
    Collection<GraphNode> totalMoneyAmountPerDate;
    Double averageMoneyAmountPerDate;
    Double totalMoneyAmountForPeriod;
}

