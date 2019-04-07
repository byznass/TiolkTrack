package com.byznass.tiolktrack.kernel.model;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

@Singleton
public class Gps {

	private final String id;
	private final List<Location> locationsInTime;

	public Gps(String id, List<Location> locationsInTime) {

		this.id = requireNonNull(id, "id cannot be null");

		this.locationsInTime = new ArrayList<>(requireNonNull(locationsInTime, "locationsInTime cannot be null"));
		this.locationsInTime.sort(comparing(Location::getTime));
	}

	public String getId() {

		return id;
	}

	public Optional<Location> getLastLocation() {

		return locationsInTime.isEmpty()
				? Optional.empty()
				: Optional.of(locationsInTime.get(locationsInTime.size() - 1));
	}

	public List<Location> getLocationsInTime() {

		return unmodifiableList(locationsInTime);
	}
}