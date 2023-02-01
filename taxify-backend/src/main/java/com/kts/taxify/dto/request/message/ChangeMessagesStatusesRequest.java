package com.kts.taxify.dto.request.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangeMessagesStatusesRequest {
    @NotEmpty
    List<UUID> messagesIds;
    @NotEmpty
    String status;
}
