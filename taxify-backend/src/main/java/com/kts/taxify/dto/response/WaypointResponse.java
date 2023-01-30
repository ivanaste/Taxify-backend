package com.kts.taxify.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WaypointResponse {
   private Double longitude;

   private Double latitude;

   private boolean stop;
}
