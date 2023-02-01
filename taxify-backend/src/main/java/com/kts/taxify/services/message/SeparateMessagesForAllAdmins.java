package com.kts.taxify.services.message;

import com.kts.taxify.dto.response.ChatResponse;
import com.kts.taxify.dto.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeparateMessagesForAllAdmins {

    public Collection<ChatResponse> execute(Collection<ChatResponse> chats) {
        List<ChatResponse> response = new ArrayList<>(chats);
        for (ChatResponse chat : chats) {
            Collection<MessageResponse> messagesForAllAdmins = extractMessagesForAllAdmins(chat.getMessages());
            if (messagesForAllAdmins.size() > 0) {
                chat.setMessages(removeMessagesForAllAdminsFromChatMessages(chat.getMessages(), messagesForAllAdmins));
                if (chat.getMessages().size() == 0) {
                    response.remove(chat);
                }
                ChatResponse newChat = ChatResponse.builder()
                        .messages(messagesForAllAdmins)
                        .continuable(chat.isContinuable())
                        .build();
                if (!chats.contains(newChat)) {
                    response.add(0, newChat);
                }
            }
        }
        return response;
    }

    private Collection<MessageResponse> extractMessagesForAllAdmins(Collection<MessageResponse> messages) {
        Collection<MessageResponse> messagesForAllAdmins = new ArrayList<>();
        for (MessageResponse message : messages) {
            if (message.getReceiver() == null) {
                messagesForAllAdmins.add(message);
            }
        }
        return messagesForAllAdmins;
    }

    private Collection<MessageResponse> removeMessagesForAllAdminsFromChatMessages(Collection<MessageResponse> chatMessages, Collection<MessageResponse> messagesForAllAdmins) {
        for (MessageResponse message : messagesForAllAdmins) {
            chatMessages.remove(message);
        }
        return chatMessages;
    }
}
