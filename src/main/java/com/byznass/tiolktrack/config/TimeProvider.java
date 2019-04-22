package com.byznass.tiolktrack.config;

import java.time.LocalDateTime;

public interface TimeProvider {

	LocalDateTime getCurrentTime();
}
