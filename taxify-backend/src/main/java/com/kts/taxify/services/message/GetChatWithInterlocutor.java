package com.kts.taxify.services.message;

import com.kts.taxify.dto.response.ChatResponse;
import com.kts.taxify.model.Message;
import com.kts.taxify.model.User;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GetChatWithInterlocutor {
    private final GetUserByEmail getUserByEmail;
    private final GetAllSelfMessages getAllSelfMessages;
    private final ExtractChatFromMessagesForReceiver extractChatFromMessagesForReceiver;

    public ChatResponse execute(@Nullable String interlocutorEmail) {
        final Collection<Message> selfMessages = getAllSelfMessages.execute();
        final User receiver = interlocutorEmail == null ? null : getUserByEmail.execute(interlocutorEmail);
        return extractChatFromMessagesForReceiver.execute(selfMessages, receiver);
    }
}
