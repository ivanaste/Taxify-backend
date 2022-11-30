package com.kts.taxify.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

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

	@Column(name = "role")
	private UserRole role;

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
}
