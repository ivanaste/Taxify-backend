package com.kts.taxify.services.message;

import com.kts.taxify.dto.response.ChatResponse;
import com.kts.taxify.dto.response.UserResponse;
import com.kts.taxify.model.Message;
import com.kts.taxify.model.User;
import com.kts.taxify.services.auth.GetSelf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GetAllChats {
    private final GetSelf getSelf;
    private final GetAllSelfMessages getAllSelfMessages;
    private final ExtractChatFromMessagesForReceiver extractChatFromMessagesForReceiver;

    public Collection<ChatResponse> execute() {
        return sortIntoChats(getAllSelfMessages.execute());
    }

    private Collection<ChatResponse> sortIntoChats(final Collection<Message> messages) {
        final Collection<User> receivers = extractReceivers(messages);
        Collection<ChatResponse> allChats = new ArrayList<>();
        for (User receiver : receivers) {
            allChats.add(extractChatFromMessagesForReceiver.execute(messages, receiver));
        }
        return allChats;
    }

    private Collection<User> extractReceivers(final Collection<Message> messages) {
        final UserResponse self = getSelf.execute();
        Collection<User> receivers = new ArrayList<>();
        for (Message message : messages) {
            if (!receivers.contains(message.getReceiver()) && (message.getReceiver() == null || (message.getReceiver() != null && !message.getReceiver().getEmail().equals(self.getEmail())))) {
                receivers.add(message.getReceiver());
            }
        }
        return receivers;
    }
}