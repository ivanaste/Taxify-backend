package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.User;
import com.kts.taxify.services.user.SaveUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddMessageToReceiver {
    private final SaveUser saveUser;
    
    public User execute(User receiver, Message message) {
        Set<Message> receivedMessages = receiver.getReceivedMessages();
        receivedMessages.add(message);
        receiver.setReceivedMessages(receivedMessages);
        return saveUser.execute(receiver);
    }
}
