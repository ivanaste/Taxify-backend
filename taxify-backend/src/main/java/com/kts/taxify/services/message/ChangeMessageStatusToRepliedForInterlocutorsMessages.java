package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.MessageStatus;
import com.kts.taxify.model.User;
import com.kts.taxify.services.user.SaveUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeMessageStatusToRepliedForInterlocutorsMessages {
    private final ChangeMessageStatus changeMessageStatus;
    private final SaveUser saveUser;

    public User execute(User interlocutor) {
        List<Message> sentMessages = new ArrayList<>(interlocutor.getSentMessages());
        for (int i = 0; i < sentMessages.size(); i++) {
            Message message = sentMessages.get(i);
            sentMessages.set(i, changeMessageStatus.execute(message, MessageStatus.REPLIED));
        }
        interlocutor.setSentMessages(new HashSet<>(sentMessages));
        return saveUser.execute(interlocutor);
    }

}
