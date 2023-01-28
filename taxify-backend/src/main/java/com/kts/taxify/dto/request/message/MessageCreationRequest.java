package com.kts.taxify.dto.request.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageCreationRequest {
    @NotEmpty
    @Email
    String senderEmail;
    @NotEmpty
    String content;
    @Email
    String receiverEmail;
}
