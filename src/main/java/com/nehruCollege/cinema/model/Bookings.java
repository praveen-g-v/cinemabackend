package com.nehruCollege.cinema.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;




@Document(value= "Booking")
public class Bookings {
	
	@Id
    private String BookingId;
		
	private String userId;
	
	private String showTimeId;
	
	private Date date;
	
	private String theatreHallId;
	
	private String[] seats;

	public String getBookingId() {
		return BookingId;
	}

	public void setBookingId(String bookingId) {
		BookingId = bookingId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShowTimeId() {
		return showTimeId;
	}

	public void setShowTimeId(String showTimeId) {
		this.showTimeId = showTimeId;
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

	public String[] getSeats() {
		return seats;
	}

	public void setSeats(String[] seats) {
		this.seats = seats;
	}

}
