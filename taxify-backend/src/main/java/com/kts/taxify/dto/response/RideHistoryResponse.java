package com.kts.taxify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RideHistoryResponse {
    private UUID id;
    private String route;
    private List<String> fullLocationNames;
    private List<UserResponse> passengers;
    private String price;
    private LocalDateTime scheduledAt;
    private LocalDateTime finishedAt;
}
