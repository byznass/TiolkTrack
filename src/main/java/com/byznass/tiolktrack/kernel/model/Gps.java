package com.byznass.tiolktrack.kernel.model;

public class Gps {

	private String id;
	private Location currentLocation;

	public Location getCurrentLocation() {

		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {

		this.currentLocation = currentLocation;
	}

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
	}
}
