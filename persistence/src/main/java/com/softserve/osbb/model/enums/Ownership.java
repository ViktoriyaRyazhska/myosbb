package com.softserve.osbb.model.enums;

import java.io.Serializable;

public enum Ownership implements Serializable {
	OWNER,SUBOWNER,RENTER;

	Ownership() {
	}
	
	public String getOwnersip() {
	    return this.name();
	}
	
}
