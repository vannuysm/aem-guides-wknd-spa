package com.wyndham.redesign.core.entity;

import com.google.gson.Gson;
/**
 * @Type Abstract Class
 * This is an abstract class about all the entity object
 */
public abstract class AbstractEntity {
	
	public String toJSON(){
		return new Gson().toJson(this);
	}
	
	@Override
	public boolean equals(Object o) {
	   return this == o || o instanceof AbstractEntity;
	}

	@Override
	public int hashCode() {
	    return AbstractEntity.class.hashCode();
	}
}
