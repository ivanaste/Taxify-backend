package com.kts.taxify.services.message;

import com.kts.taxify.converter.MessageConverter;
import com.kts.taxify.dto.request.message.ChangeMessagesStatusesRequest;
import com.kts.taxify.dto.response.MessageResponse;
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

    public Collection<MessageResponse> execute(ChangeMessagesStatusesRequest changeMessagesStatusesRequest) {
        Collection<MessageResponse> responseMessages = new ArrayList<>();
        for (String messageId : changeMessagesStatusesRequest.getMessagesIds()) {
            responseMessages.add(MessageConverter.toMessageResponse(changeMessageStatus.execute(getMessageById.execute(messageId), MessageStatus.valueOf(changeMessagesStatusesRequest.getStatus()))));
        }
        return responseMessages;
    }
}
