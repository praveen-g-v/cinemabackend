package com.nehruCollege.cinema.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "ShowTime")
public class ShowTime {
	
	@Id
    private String showTimeId;
	
	private String movieId;
	
	private Date date;
	
	private String theatreHallId;
	
	private String[][] ticketLayout;
	
	private int availableSeats;

	public String getShowTimeId() {
		return showTimeId;
	}

	public void setShowTimeId(String showTimeId) {
		this.showTimeId = showTimeId;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTheatreHallId() {
		return theatreHallId;
	}

	public void setTheatreHallId(String theatreHallId) {
		this.theatreHallId = theatreHallId;
	}

	public String[][] getTicketLayout() {
		return ticketLayout;
	}

	public void setTicketLayout(String[][] ticketLayout) {
		this.ticketLayout = ticketLayout;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
	
	
	
}
