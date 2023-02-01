package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.User;
import com.kts.taxify.services.user.SaveUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddMessageToSender {
    private final SaveUser saveUser;

    public User execute(User sender, final Message message) {
        Set<Message> sentMessages = sender.getSentMessages();
        sentMessages.add(message);
        sender.setSentMessages(sentMessages);
        return saveUser.execute(sender);
    }
}
