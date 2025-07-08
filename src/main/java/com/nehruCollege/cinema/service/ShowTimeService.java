package com.nehruCollege.cinema.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.ShowTime;
import com.nehruCollege.cinema.model.TheatreHall;
import com.nehruCollege.cinema.repository.MovieRepository;
import com.nehruCollege.cinema.repository.ShowTimeRepository;
import com.nehruCollege.cinema.repository.TheatreHallRepository;


@Service
public class ShowTimeService {
	
	@Autowired
	private ShowTimeRepository showTimeRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TheatreHallRepository theatreHallRepository;
	
	public ShowTime createShowTime(ShowTime showTime) throws ServiceException {
		if(showTime.getMovieId()!=null) {
			if(showTime.getTheatreHallId()!=null) {
				movieRepository.findById(showTime.getMovieId()).orElseThrow(()-> new ServiceException("Invalid Movie: Please provide valid one",HttpStatus.CONFLICT));
				TheatreHall hall= theatreHallRepository.findById(showTime.getTheatreHallId()).orElseThrow(()-> new ServiceException("Invalid ThaetreHall: Please provide valid one",HttpStatus.CONFLICT));
				showTime.setTicketLayout(hall.getLayout());
				showTime.setAvailableSeats(hall.getTotalAvailableSeats());
				
			}
			else {
				throw new ServiceException("Please provide valid Thetare Hall",HttpStatus.CONFLICT);
			}
		}
		else {
			throw new ServiceException("Please provide valid movie",HttpStatus.CONFLICT);
		}
		try {
			return showTimeRepository.save(showTime);
		}
		catch(Exception e) {
			throw new ServiceException("Unable to Store Show Time Details",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public List<ShowTime> getCurrentlyAiringShows() throws ServiceException{
		try {
			ZonedDateTime zonedNow = ZonedDateTime.now();
			ZonedDateTime zonedTomorrow = zonedNow.plusDays(1);
			
			return showTimeRepository.findByDateBetween(new Date(),Date.from(zonedTomorrow.toInstant()));
		}catch (Exception e) {
			throw new ServiceException("Unable to get Shows Airings",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
