package com.kts.taxify.repository;

import com.kts.taxify.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    Collection<Notification> findAllByRecipientIsNullAndReadIsFalse();
}
