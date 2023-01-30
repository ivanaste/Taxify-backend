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
public class GetAllChats {
    private final GetAllSelfMessages getAllSelfMessages;

    public Collection<ChatResponse> execute() {
        return sortIntoChats(getAllSelfMessages.execute());
    }

    private Collection<ChatResponse> sortIntoChats(final Collection<Message> messages) {
        final Collection<User> receivers = extractReceivers(messages);
        Collection<ChatResponse> allChats = new ArrayList<>();
        for (User receiver : receivers) {
            allChats.add(filterChatForReceiver(messages, receiver));
        }
        return allChats;
    }

    private Collection<User> extractReceivers(final Collection<Message> messages) {
        Collection<User> receivers = new ArrayList<>();
        for (Message message : messages) {
            if (!receivers.contains(message.getReceiver())) {
                receivers.add(message.getReceiver());
            }
        }
        return receivers;
    }

    private ChatResponse filterChatForReceiver(final Collection<Message> messages, @Nullable final User receiver) {
        Collection<MessageResponse> chatMessages = new ArrayList<>();
        for (Message message : messages) {
            if ((message.getReceiver() != null && message.getReceiver().equals(receiver)) || message.getReceiver() == receiver) {
                chatMessages.add(MessageConverter.toMessageResponse(message));
            }
        }
        return MessageConverter.toChatResponse(chatMessages);
    }
}