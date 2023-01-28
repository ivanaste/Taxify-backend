package com.kts.taxify.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message extends BaseEntity {

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    MessageStatus status;

    String content;

    @Column(name = "created_on", nullable = false)
    LocalDateTime createdOn;

    @Column(name = "seen_on")
    LocalDateTime seenOn;

    @Column(name = "delivered_on")
    LocalDateTime deliveredOn;

    @Column(name = "replied_on")
    LocalDateTime repliedOn;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    User receiver;
}
