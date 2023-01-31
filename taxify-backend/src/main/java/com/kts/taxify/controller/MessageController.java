package com.kts.taxify.controller;

import com.kts.taxify.dto.request.message.ChangeMessagesStatusesRequest;
import com.kts.taxify.dto.request.message.MessageCreationRequest;
import com.kts.taxify.dto.response.ChatResponse;
import com.kts.taxify.dto.response.MessageResponse;
import com.kts.taxify.services.message.ChangeMessagesStatuses;
import com.kts.taxify.services.message.CreateMessage;
import com.kts.taxify.services.message.GetAllChats;
import com.kts.taxify.services.message.GetChatWithInterlocutor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final GetAllChats getAllChats;
    private final GetChatWithInterlocutor getChatWithInterlocutor;
    private final CreateMessage createMessage;
    private final ChangeMessagesStatuses changeMessagesStatuses;

    @GetMapping("/all")
    public Collection<ChatResponse> getAllChats() {
        return getAllChats.execute();
    }
    
    @GetMapping("/chat-with-interlocutor")
    public ChatResponse getChatWithInterlocutor(@RequestParam String interlocutorEmail) {
        return getChatWithInterlocutor.execute(interlocutorEmail);
    }

    @PostMapping("/send")
    public MessageResponse sendMessage(@Valid @RequestBody final MessageCreationRequest messageCreationRequest) {
        return createMessage.execute(messageCreationRequest);
    }

    @PutMapping("/status")
    public Collection<MessageResponse> changeMessagesStatuses(@Valid @RequestBody final ChangeMessagesStatusesRequest changeMessagesStatusesRequest) {
        return changeMessagesStatuses.execute(changeMessagesStatusesRequest);
    }
}
