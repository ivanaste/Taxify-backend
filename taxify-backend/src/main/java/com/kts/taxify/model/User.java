package com.kts.taxify.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class User extends BaseEntity {

	@Column(name = "email", nullable = false)
	String email;
	@Column(name = "password", nullable = false)
	String password;
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

	@Value("false")
	@Column(name = "blocked", nullable = false)
	boolean blocked;
}
