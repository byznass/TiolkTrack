package com.byznass.tiolktrack.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class SystemTimeProvider implements TimeProvider {

	@Override
	public LocalDateTime getCurrentTime() {

		return ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
	}
}
