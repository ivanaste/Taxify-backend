package com.kts.taxify.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "passenger")
public class Passenger extends User {

	@ManyToMany(mappedBy = "passengers")
	Set<Ride> rides;

	@ManyToMany
	@JoinTable(name = "passenger_favorite_routes", joinColumns = @JoinColumn(name = "passenger_id"),
		inverseJoinColumns = @JoinColumn(name = "route_id"))
	Set<Route> favoriteRoutes;

	@Value("false")
	@Column(name = "activated", nullable = false)
	Boolean activated;

	@Value("false")
	@Column(name = "in_process", nullable = false)
	Boolean inProcess; //lose ime -> korisnik zahtevao voznju ili se vozi
}
