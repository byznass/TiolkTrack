package com.byznass.tiolktrack.kernel.model;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;

public class User {

	private final String id;
	private final byte[] passHash;
	private final byte[] passSalt;

	public User(@NotNull String id, @NotNull byte[] passHash, @NotNull byte[] passSalt) throws RuntimeException {

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

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}

		User user = (User) o;

		return id.equals(user.id) &&
				Arrays.equals(passHash, user.passHash) &&
				Arrays.equals(passSalt, user.passSalt);
	}

	@Override
	public int hashCode() {

		int result = Objects.hash(id);
		result = 31 * result + Arrays.hashCode(passHash);
		result = 31 * result + Arrays.hashCode(passSalt);
		return result;
	}
}
