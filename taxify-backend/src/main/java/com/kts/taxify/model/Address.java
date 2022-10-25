package com.kts.taxify.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class Address {
	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "zipCode", nullable = false)
	private String zipCode;

}
