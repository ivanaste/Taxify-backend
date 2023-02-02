package com.kts.taxify.simulatorModel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RideProcessData {
    private Process process;
    private LocalDateTime start;
    private LocalDateTime expectedEnd;
}
