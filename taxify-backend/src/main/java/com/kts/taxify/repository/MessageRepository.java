package com.kts.taxify.repository;

import com.kts.taxify.model.Message;
import com.kts.taxify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    Collection<Message> findAllBySenderOrReceiver(User sender, User receiver);

    Collection<Message> findAllBySenderOrReceiverOrReceiverIsNull(User sender, User receiver);
}