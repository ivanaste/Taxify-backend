package com.kts.taxify.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@SuperBuilder
public class User extends BaseEntity {
    @Column(name = "email", nullable = false)
    String email;

    @ManyToOne
    @JsonManagedReference
    Role role;

    @Column(name = "password_hash", nullable = false, length = 60)
    @JsonProperty(access = Access.WRITE_ONLY)
    @NotNull
    String passwordHash;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "surname", nullable = false)
    String surname;

    @Column(name = "city", nullable = false)
    String city;

    @Column(name = "phone_number", nullable = false)
    String phoneNumber;

    @Column(name = "profile_picture")
    String profilePicture;

    @Column(name = "blocked")
    @Value("false")
    boolean blocked;

    @Enumerated(EnumType.STRING)
    AccountProvider accountProvider;

    @Column(name = "received_messages")
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    Set<Message> receivedMessages;

    @Column(name = "sent_messages")
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    Set<Message> sentMessages;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    Set<Notification> sentNotifications;

    @OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY)
    Set<Notification> receivedNotifications;
}
