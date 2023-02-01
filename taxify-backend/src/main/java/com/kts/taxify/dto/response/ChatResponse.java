package com.kts.taxify.dto.response;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    boolean continuable;
    Collection<MessageResponse> messages;
}
