package com.kts.taxify.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "notification")
public class Notification extends BaseEntity {

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	NotificationType type;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sender_id")
	Passenger sender;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "notification_recipient", joinColumns = @JoinColumn(name = "notification_id"),
		inverseJoinColumns = @JoinColumn(name = "passenger_id"))
	Set<Passenger> recipients;

	@Column(name = "arrivalTime")
	LocalDateTime arrivalTime;

	@Column(name = "read", nullable = false)
	boolean read;
}
