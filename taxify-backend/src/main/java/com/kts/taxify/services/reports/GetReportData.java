package com.kts.taxify.services.reports;

import com.kts.taxify.converter.ReportDataConverter;
import com.kts.taxify.dto.response.ReportResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GetReportData {
    private final GetNumberOfKilometersPerDayInPeriod getNumberOfKilometersPerDayInPeriod;
    private final GetNumberOfRidesPerDayInPeriod getNumberOfRidesPerDayInPeriod;
    private final GetMoneyPerDayInPeriod getMoneyPerDayInPeriod;

    public ReportResponse execute(LocalDate initDate, LocalDate termDate) {
        Map<LocalDate, Double> numberOfKilometersPerDayInPeriod = getNumberOfKilometersPerDayInPeriod.execute(initDate, termDate);
        Map<LocalDate, Integer> numberOfRidesPerDayInPeriod = getNumberOfRidesPerDayInPeriod.execute(initDate, termDate);
        Map<LocalDate, Double> amountOfMoneyPerDayInPeriod = getMoneyPerDayInPeriod.execute(initDate, termDate);

        return ReportDataConverter.toReportResponse(numberOfKilometersPerDayInPeriod, numberOfRidesPerDayInPeriod, amountOfMoneyPerDayInPeriod);
    }
}
