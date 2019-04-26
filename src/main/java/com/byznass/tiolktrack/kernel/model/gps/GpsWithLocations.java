package com.byznass.tiolktrack.kernel.model.gps;

import com.byznass.tiolktrack.kernel.model.Location;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

@Singleton
public class GpsWithLocations {

	private final Gps gps;
	private final List<Location> locationsInTime;

	public GpsWithLocations(Gps gps, List<Location> locationsInTime) {

		this.gps = requireNonNull(gps, "gps cannot be null");

		this.locationsInTime = new ArrayList<>(requireNonNull(locationsInTime, "locationsInTime cannot be null"));
		this.locationsInTime.sort(comparing(Location::getTime));
	}

	public Gps getGps() {

		return gps;
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