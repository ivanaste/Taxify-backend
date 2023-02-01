package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notification")
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ride_id")
    Ride ride;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    NotificationType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    User sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    Passenger recipient;

    @Column(name = "arrivalTime")
    LocalDateTime arrivalTime;

    @Column(name = "read", nullable = false)
    boolean read;

    @Column(name = "status", columnDefinition = "varchar(32) default 'PENDING'")
    @Enumerated(EnumType.STRING)
    NotificationStatus status = NotificationStatus.PENDING;
}
