package com.kts.taxify.dto.request.notification;

import java.util.Set;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LinkedPassengersToTheRideRequest {
	String senderEmail;

	Set<String> recipientsEmails;

}
