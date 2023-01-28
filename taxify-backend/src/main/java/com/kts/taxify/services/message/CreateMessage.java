package com.kts.taxify.services.message;

import com.kts.taxify.dto.request.message.MessageCreationRequest;
import com.kts.taxify.model.Message;
import com.kts.taxify.model.MessageStatus;
import com.kts.taxify.model.User;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMessage {
    private final GetUserByEmail getUserByEmail;
    private final AddMessageToSender addMessageToSender;
    private final AddMessageToReceiver addMessageToReceiver;
    private final ChangeMessageStatus changeMessageStatus;
    private final SetRepliedStatusToReceiversSeenMessages setRepliedStatusToReceiversSeenMessages;

    public Message execute(final MessageCreationRequest messageCreationRequest) {
        User sender = getUserByEmail.execute(messageCreationRequest.getSenderEmail());
        User receiver = messageCreationRequest.getReceiverEmail() != null ? getUserByEmail.execute(messageCreationRequest.getReceiverEmail()) : null;
        Message message = Message.builder()
                .content(messageCreationRequest.getContent())
                .sender(sender)
                .build();
        if (receiver != null) {
            message.setReceiver(receiver);
            setRepliedStatusToReceiversSeenMessages.execute(receiver);
        }
        message = changeMessageStatus.execute(message, MessageStatus.SENT);
        addMessageToSender.execute(sender, message);
        if (receiver != null) {
            addMessageToReceiver.execute(receiver, message);
        }
        return message;
    }
}