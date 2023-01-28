package com.kts.taxify.dto.request.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ToggleIsBlockedRequest {
    String email;
    String reason;
}
