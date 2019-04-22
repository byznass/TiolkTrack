package com.byznass.tiolktrack.kernel.model;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class User {

	private final String id;
	private final byte[] passHash;
	private final byte[] passSalt;

	public User(@NotNull String id, @NotNull byte[] passHash, @NotNull byte[] passSalt) {

		if (id == null || passHash == null || passSalt == null) {
			throw new IllegalArgumentException("All parameters must be non null.");
		}

		this.id = id;
		this.passHash = Arrays.copyOf(passHash, passHash.length);
		this.passSalt = Arrays.copyOf(passSalt, passSalt.length);
	}

	public String getId() {

		return id;
	}

	public byte[] getPassHash() {

		return Arrays.copyOf(passHash, passHash.length);
	}

	public byte[] getPassSalt() {

		return Arrays.copyOf(passSalt, passSalt.length);
	}
}
