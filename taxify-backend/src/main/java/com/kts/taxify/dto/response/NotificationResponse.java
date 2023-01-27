package com.kts.taxify.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {

	@NotEmpty
	UUID id;

	@NotEmpty
	String type;

	@NotEmpty
	String senderName;

	@NotEmpty
	String senderSurname;

	@NotEmpty
	LocalDateTime arrivalTime;

	@NotEmpty
	boolean read;

	@NotEmpty
	String status;
}
