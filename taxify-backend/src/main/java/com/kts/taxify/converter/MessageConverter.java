package com.kts.taxify.converter;

import com.kts.taxify.dto.response.ChatResponse;
import com.kts.taxify.dto.response.MessageResponse;
import com.kts.taxify.model.Message;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class MessageConverter {
    private final static ModelMapper modelMapper = new ModelMapper();

    public static MessageResponse toMessageResponse(final Message message) {
        MessageResponse messageResponse = modelMapper.map(message, MessageResponse.class);
        messageResponse.setSender(UserConverter.toUserResponse(message.getSender()));
        if (message.getReceiver() != null) {
            messageResponse.setReceiver(UserConverter.toUserResponse(message.getReceiver()));
        }
        return messageResponse;
    }

    public static ChatResponse toChatResponse(final Collection<MessageResponse> messages) {
        ((List<MessageResponse>) messages).sort(new SortByDateOfCreation());
        return ChatResponse.builder()
                .messages(messages)
                .continuable((getLastMessage(messages).getCreatedOn().plusDays(7)).isAfter(LocalDateTime.now()))
                .build();
    }

    private static MessageResponse getLastMessage(final Collection<MessageResponse> messages) {
        return messages.stream().findFirst().orElseThrow();
    }
}

class SortByDateOfCreation implements Comparator<MessageResponse> {

    @Override
    public int compare(MessageResponse m1, MessageResponse m2) {
        return m1.getCreatedOn().equals(m2.getCreatedOn()) ? 0 : (m1.getCreatedOn().isBefore(m2.getCreatedOn()) ? 1 : -1);
    }
}
