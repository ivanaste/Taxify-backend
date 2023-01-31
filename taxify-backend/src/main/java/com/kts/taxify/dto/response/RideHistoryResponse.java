package com.kts.taxify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RideHistoryResponse {
    private UUID id;
    private String route;
    private String price;
    private LocalDateTime scheduledAt;
    private LocalDateTime finishedAt;
}
