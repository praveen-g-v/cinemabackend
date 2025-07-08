package com.nehruCollege.cinema.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nehruCollege.cinema.enums.AgeRestriction;


@Document(value = "Movie")
public class Movie {
	
	@Id
    private String movieId;
	
	
	private String title;
	
	private String genre;
	
	private int duration;
	
	private String synopsis;
	
	private	Cast[] cast;
	
	private Crew[] crew;
	
	private String imageId;
	
	private AgeRestriction ageRestriction;
	
	private String trailer;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Cast[] getCast() {
		return cast;
	}

	public void setCast(Cast[] cast) {
		this.cast = cast;
	}

	public Crew[] getCrew() {
		return crew;
	}

	public void setCrew(Crew[] crew) {
		this.crew = crew;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public AgeRestriction getAgeRestriction() {
		return ageRestriction;
	}

	public void setAgeRestriction(AgeRestriction ageRestriction) {
		this.ageRestriction = ageRestriction;
	}

	public String getTrailer() {
		return trailer;
	}

	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}

	

	
	
	
	

}
