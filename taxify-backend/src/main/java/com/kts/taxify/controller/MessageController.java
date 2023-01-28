package com.kts.taxify.controller;

import com.kts.taxify.dto.request.message.ChangeMessagesStatusesRequest;
import com.kts.taxify.dto.request.message.MessageCreationRequest;
import com.kts.taxify.model.Message;
import com.kts.taxify.services.message.ChangeMessagesStatuses;
import com.kts.taxify.services.message.CreateMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final CreateMessage createMessage;
    private final ChangeMessagesStatuses changeMessagesStatuses;

    @PostMapping("/send")
    public Message sendMessage(@Valid @RequestBody final MessageCreationRequest messageCreationRequest) {
        return createMessage.execute(messageCreationRequest);
    }

    @PutMapping("/status")
    public Collection<Message> changeMessagesStatuses(@Valid @RequestBody final ChangeMessagesStatusesRequest changeMessagesStatusesRequest) {
        return changeMessagesStatuses.execute(changeMessagesStatusesRequest);
    }
}
