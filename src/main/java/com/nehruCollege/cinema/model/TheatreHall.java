package com.nehruCollege.cinema.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "Theatre Hall")
public class TheatreHall {
	
	@Id
    private String theatreHallId;

	private String name;
	
	private int seats;
	
	private int row;
	
	private String location;
	
	private String[][] layout;
	
	private int totalAvailableSeats;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String[][] getLayout() {
		return layout;
	}

	public void setLayout(String[][] layout) {
		this.layout = layout;
	}

	public int getTotalAvailableSeats() {
		return totalAvailableSeats;
	}

	public void setTotalAvailableSeats(int totalAvailableSeats) {
		this.totalAvailableSeats = totalAvailableSeats;
	}
	
}
