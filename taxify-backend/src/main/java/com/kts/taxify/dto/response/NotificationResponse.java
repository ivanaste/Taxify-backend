package com.kts.taxify.dto.response;

import com.kts.taxify.model.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

    @NotEmpty
    UUID id;

    @NotEmpty
    String type;

    @NotEmpty
    String senderName;

    @NotEmpty
    String senderSurname;

    @NotEmpty
    LocalDateTime arrivalTime;

    @NotEmpty
    boolean read;

    String userStatusChangeReason;

    @NotEmpty
    NotificationStatus status;
}
