package com.kts.taxify.services.message;

import com.kts.taxify.converter.MessageConverter;
import com.kts.taxify.dto.response.ChatResponse;
import com.kts.taxify.dto.response.MessageResponse;
import com.kts.taxify.model.Message;
import com.kts.taxify.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ExtractChatFromMessagesForReceiver {

    public ChatResponse execute(final Collection<Message> messages, @Nullable final User receiver) {
        Collection<MessageResponse> chatMessages = new ArrayList<>();
        for (Message message : messages) {
            if ((message.getReceiver() != null && (message.getReceiver().equals(receiver) || message.getSender().equals(receiver))) || message.getReceiver() == receiver) {
                chatMessages.add(MessageConverter.toMessageResponse(message));
            }
        }
        return MessageConverter.toChatResponse(chatMessages);
    }
}
