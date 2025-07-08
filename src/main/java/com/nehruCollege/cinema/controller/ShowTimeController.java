package com.nehruCollege.cinema.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.ShowTime;
import com.nehruCollege.cinema.service.ShowTimeService;

@RestController
@RequestMapping("/api/showtime")
public class ShowTimeController {
	
	@Autowired
	private ShowTimeService showTimeService;
	
	
	@PostMapping
	public ResponseEntity<?> createShowtime(
			@RequestParam(value = "movieId" , required = true)
			String movieId,
			@RequestParam(value = "date" , required = true)
			long date,
			@RequestParam(value = "theatreHallId" , required = true)
			String theatreHallId
			) throws ServiceException{
		ShowTime showTime=new ShowTime();
		showTime.setMovieId(movieId);
		showTime.setDate(new Date(date));
		showTime.setTheatreHallId(theatreHallId);
		showTimeService.createShowTime(showTime);
		return  ResponseEntity.ok("Created ShowTime Successfully!");
	}

}
