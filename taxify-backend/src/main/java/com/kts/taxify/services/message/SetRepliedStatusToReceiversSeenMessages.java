package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.MessageStatus;
import com.kts.taxify.model.User;
import com.kts.taxify.services.user.SaveUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SetRepliedStatusToReceiversSeenMessages {
    private final SaveMessage saveMessage;
    private final SaveUser saveUser;

    public User execute(User receiver) {
        List<Message> sentMessages = new ArrayList<>(receiver.getSentMessages());
        for (int i = 0; i < sentMessages.size(); i++) {
            Message message = sentMessages.get(i);
            if (message.getStatus().equals(MessageStatus.SEEN)) {
                sentMessages.remove(message);
                message.setStatus(MessageStatus.REPLIED);
                message.setRepliedOn(LocalDateTime.now());
                sentMessages.add(i, saveMessage.execute(message));
            }
        }
        receiver.setSentMessages(new HashSet<>(sentMessages));
        return saveUser.execute(receiver);
    }
}
