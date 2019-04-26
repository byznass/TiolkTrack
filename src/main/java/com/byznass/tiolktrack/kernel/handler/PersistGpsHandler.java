package com.byznass.tiolktrack.kernel.handler;

import com.byznass.tiolktrack.kernel.model.gps.Gps;
import com.byznass.tiolktrack.kernel.util.IdentifierValidator;

public class PersistGpsHandler {

	public Gps persistGps(Gps gps){



		return null;
	}

	private void validate(Gps gps){

		if(!IdentifierValidator.validate(gps.getName())){
			throw new
		}
	}

}
