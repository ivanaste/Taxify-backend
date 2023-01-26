package com.kts.taxify.repository;

import com.kts.taxify.model.Notification;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

}
