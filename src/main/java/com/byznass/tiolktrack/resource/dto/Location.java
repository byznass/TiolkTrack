package com.byznass.tiolktrack.resource.dto;

import java.util.Objects;

public class Location {

    private String longitude;
    private String latitude;

    public Location(String longitude, String latitude) {

        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {

        return longitude;
    }

    public void setLongitude(String longitude) {

        this.longitude = longitude;
    }

    public String getLatitude() {

        return latitude;
    }

    public void setLatitude(String latitude) {

        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Location)) {
            return false;
        }

        Location location = (Location) o;

        return longitude.equals(location.longitude) &&
                latitude.equals(location.latitude);
    }

    @Override
    public int hashCode() {

        return Objects.hash(longitude, latitude);
    }
}
