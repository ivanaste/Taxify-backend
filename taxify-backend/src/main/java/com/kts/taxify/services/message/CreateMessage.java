package com.kts.taxify.services.message;

import com.kts.taxify.converter.MessageConverter;
import com.kts.taxify.dto.request.message.MessageCreationRequest;
import com.kts.taxify.dto.response.MessageResponse;
import com.kts.taxify.model.Message;
import com.kts.taxify.model.User;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.notification.CreateAdminNotification;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMessage {
    private final GetSelf getSelf;
    private final SendMessage sendMessage;
    private final GetUserByEmail getUserByEmail;
    private final AddMessageToSender addMessageToSender;
    private final AddMessageToReceiver addMessageToReceiver;
    private final ChangeMessageStatus changeMessageStatus;
    private final ChangeMessageStatusToSent changeMessageStatusToSent;
    private final ChangeMessageStatusToRepliedForInterlocutorsMessages changeMessageStatusToRepliedForInterlocutorsMessages;
    private final CreateAdminNotification createAdminNotification;

    public MessageResponse execute(final MessageCreationRequest messageCreationRequest) {
        User sender = getUserByEmail.execute(getSelf.execute().getEmail());
        User receiver = messageCreationRequest.getReceiverEmail() != null ? getUserByEmail.execute(messageCreationRequest.getReceiverEmail()) : null;
        Message message = Message.builder()
                .content(messageCreationRequest.getContent())
                .sender(sender)
                .receiver(receiver)
                .build();
        // If receiver exists set all his previous messages' status to REPLIED
        if (receiver != null) {
            changeMessageStatusToRepliedForInterlocutorsMessages.execute(receiver);
            // If receiver does not exist and sender role is not admin, add notification for admins
        } else if (!sender.getRole().getName().equals("ADMIN")) {
            message.setNotification(createAdminNotification.execute(message.getSender()));
        }
        // Setting message status
        message = changeMessageStatusToSent.execute(message);
        addMessageToSender.execute(sender, message);
        if (receiver != null) {
            addMessageToReceiver.execute(receiver, message);
        }
        sendMessage.execute(receiver != null ? receiver.getEmail() : null, message.getSender().getEmail());
        return MessageConverter.toMessageResponse(message);
    }
}
