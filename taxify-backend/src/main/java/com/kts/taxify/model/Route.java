package com.kts.taxify.model;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route extends BaseEntity {

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "street", column = @Column(name = "departure_street")),
		@AttributeOverride(name = "city", column = @Column(name = "departure_city")),
		@AttributeOverride(name = "state", column = @Column(name = "departure_state")),
		@AttributeOverride(name = "zipCode", column = @Column(name = "departure_zip"))
	})
	Address departure;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "street", column = @Column(name = "destination_street")),
		@AttributeOverride(name = "city", column = @Column(name = "destination_city")),
		@AttributeOverride(name = "state", column = @Column(name = "destination_state")),
		@AttributeOverride(name = "zipCode", column = @Column(name = "destination_zip"))
	})
	Address destination;

	@ManyToMany(mappedBy = "favoriteRoutes")
	Set<Passenger> subscribedPassengers;

	@Column(name = "price", nullable = false)
	Double price;

	@Column(name = "duration", nullable = false)
	Double duration;
}
