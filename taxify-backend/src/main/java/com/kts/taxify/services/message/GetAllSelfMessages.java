package com.kts.taxify.services.message;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.User;
import com.kts.taxify.repository.MessageRepository;
import com.kts.taxify.services.auth.GetSelf;
import com.kts.taxify.services.user.GetUserByEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class GetAllSelfMessages {
    private final GetSelf getSelf;
    private final GetUserByEmail getUserByEmail;
    private final MessageRepository messageRepository;

    public Collection<Message> execute() {
        final User self = getUserByEmail.execute(getSelf.execute().getEmail());
        return self.getRole().getName().equals("ADMIN") ? messageRepository.findAllBySenderOrReceiverOrReceiverIsNull(self, self) : messageRepository.findAllBySenderOrReceiver(self, self);
    }
}
