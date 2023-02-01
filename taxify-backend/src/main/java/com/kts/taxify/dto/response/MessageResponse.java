package com.kts.taxify.dto.response;

import com.kts.taxify.model.MessageStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    String id;

    MessageStatus status;

    String content;

    LocalDateTime createdOn;

    LocalDateTime seenOn;

    LocalDateTime deliveredOn;

    LocalDateTime repliedOn;

    UserResponse sender;

    UserResponse receiver;
}
