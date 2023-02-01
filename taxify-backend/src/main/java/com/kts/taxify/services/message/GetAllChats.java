package com.kts.taxify.services.message;

import com.kts.taxify.dto.response.ChatResponse;
import com.kts.taxify.model.Message;
import com.kts.taxify.model.User;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllChats {
    private final GetUserByEmail getUserByEmail;
    private final GetSelf getSelf;
    private final GetAllSelfMessages getAllSelfMessages;
    private final ExtractChatFromMessagesForInterlocutor extractChatFromMessagesForInterlocutor;
    private final SeparateMessagesForAllAdmins separateMessagesForAllAdmins;

    public Collection<ChatResponse> execute() {
        return sortIntoChats(getAllSelfMessages.execute());
    }

    private Collection<ChatResponse> sortIntoChats(final Collection<Message> messages) {
        final User self = getUserByEmail.execute(getSelf.execute().getEmail());
        final Boolean selfIsAdmin = self.getRole().getName().equals("ADMIN");
        final Collection<User> interlocutors = extractInterlocutors(messages, self.getEmail(), selfIsAdmin);
        Collection<ChatResponse> allChats = new ArrayList<>();
        for (User interlocutor : interlocutors) {
            allChats.add(extractChatFromMessagesForInterlocutor.execute(messages, interlocutor));
        }
        if (selfIsAdmin) {
            return separateMessagesForAllAdmins.execute(allChats);
        }
        return allChats;
    }

    private Collection<User> extractInterlocutors(final Collection<Message> messages, final String selfEmail, final Boolean isAdmin) {
        Collection<User> interlocutors = new ArrayList<>();
        for (Message message : messages) {
            if (!interlocutors.contains(message.getReceiver()) && !isAdmin && message.getReceiver() == null) {
                interlocutors.add(message.getReceiver());
            } else if (message.getReceiver() != null && !message.getReceiver().getEmail().equals(selfEmail) && !interlocutors.contains(message.getReceiver())) {
                interlocutors.add(message.getReceiver());
            } else if (!message.getSender().getEmail().equals(selfEmail) && !interlocutors.contains(message.getSender())) {
                interlocutors.add(message.getSender());
            }
        }
        return interlocutors;
    }
}