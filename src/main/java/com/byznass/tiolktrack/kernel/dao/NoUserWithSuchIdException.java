package com.byznass.tiolktrack.kernel.dao;

public class NoUserWithSuchIdException extends RuntimeException{

	public NoUserWithSuchIdException(String userId){
		super(String.format("User with id = '%s' does not exist.", userId));
	}
}
