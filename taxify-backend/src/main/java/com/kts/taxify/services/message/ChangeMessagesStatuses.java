package com.kts.taxify.services.message;

import com.kts.taxify.dto.request.message.ChangeMessagesStatusesRequest;
import com.kts.taxify.model.Message;
import com.kts.taxify.model.MessageStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ChangeMessagesStatuses {
    private final GetMessageById getMessageById;
    private final ChangeMessageStatus changeMessageStatus;

    public Collection<Message> execute(ChangeMessagesStatusesRequest changeMessagesStatusesRequest) {
        Collection<Message> responseMessages = new ArrayList<>();
        for (String messageId : changeMessagesStatusesRequest.getMessagesIds()) {
            responseMessages.add(changeMessageStatus.execute(getMessageById.execute(messageId), MessageStatus.valueOf(changeMessagesStatusesRequest.getStatus())));
        }
        return responseMessages;
    }
}
