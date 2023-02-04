package com.kts.taxify.converter;

import com.kts.taxify.dto.response.GraphNode;
import com.kts.taxify.dto.response.ReportResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportDataConverter {
    public static ReportResponse toReportResponse(Map<LocalDate, Double> numberOfKilometersPerDayInPeriod,
                                                  Map<LocalDate, Integer> numberOfRidesPerDayInPeriod,
                                                  Map<LocalDate, Double> amountOfMoneyPerDayInPeriod) {
        int totalNumberOfRidesForPeriod = numberOfRidesPerDayInPeriod.values().stream().mapToInt(Integer::intValue).sum();
        Double averageNumberOfRidesPerDate = (double) (totalNumberOfRidesForPeriod / numberOfRidesPerDayInPeriod.values().size());
        double totalNumberOfKilometersForPeriod = numberOfKilometersPerDayInPeriod.values().stream().mapToDouble(Double::doubleValue).sum();
        Double averageNumberOfKilometersPerDate = totalNumberOfKilometersForPeriod / numberOfKilometersPerDayInPeriod.values().size();
        double totalMoneyAmountForPeriod = amountOfMoneyPerDayInPeriod.values().stream().mapToDouble(Double::doubleValue).sum();
        Double averageMoneyAmountPerDate = totalMoneyAmountForPeriod / amountOfMoneyPerDayInPeriod.values().size();
        return ReportResponse.builder()
                .numberOfRidesPerDate(ReportDataConverter.toGraphInt(numberOfRidesPerDayInPeriod))
                .averageNumberOfRidesPerDate(averageNumberOfRidesPerDate)
                .totalNumberOfRidesForPeriod((double) totalNumberOfRidesForPeriod)
                .numberOfKilometersPerDate(ReportDataConverter.toGraphDouble(numberOfKilometersPerDayInPeriod))
                .averageNumberOfKilometersPerDate(averageNumberOfKilometersPerDate)
                .totalNumberOfKilometersForPeriod(totalNumberOfKilometersForPeriod)
                .totalMoneyAmountPerDate(ReportDataConverter.toGraphDouble(amountOfMoneyPerDayInPeriod))
                .totalMoneyAmountForPeriod(totalMoneyAmountForPeriod)
                .averageMoneyAmountPerDate(averageMoneyAmountPerDate)
                .build();
    }

    private static List<GraphNode> toGraphInt(Map<LocalDate, Integer> map) {
        List<GraphNode> graph = new ArrayList<>();
        for (LocalDate date : map.keySet()) {
            graph.add(new GraphNode(date.toString(), map.get(date).toString()));
        }
        return graph;
    }

    private static List<GraphNode> toGraphDouble(Map<LocalDate, Double> map) {
        List<GraphNode> graph = new ArrayList<>();
        for (LocalDate date : map.keySet()) {
            graph.add(new GraphNode(date.toString(), map.get(date).toString()));
        }
        return graph;
    }
}
