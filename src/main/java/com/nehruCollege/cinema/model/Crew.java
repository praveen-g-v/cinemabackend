package com.nehruCollege.cinema.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(value = "Crew")
public class Crew {

	
	private String name;
	
	private String profile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	
}
