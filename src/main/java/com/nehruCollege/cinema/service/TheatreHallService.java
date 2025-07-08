package com.nehruCollege.cinema.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.TheatreHall;
import com.nehruCollege.cinema.repository.TheatreHallRepository;

@Service
public class TheatreHallService {
	
	@Autowired
	TheatreHallRepository theatreHallRepository;
	
	public static final char[] ALPHABETS= {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	public TheatreHall addTheatreHall(TheatreHall theatreHall) throws ServiceException {
		if(theatreHall.getName().length()<=0) {
			throw new ServiceException("Please Provide Theatre Hall Name",HttpStatus.BAD_REQUEST);
		}
		if(theatreHall.getLocation().length()<=0) {
			throw new ServiceException("Please Provide Location",HttpStatus.BAD_REQUEST);
		}
		if(theatreHall.getRow()<=0||theatreHall.getSeats()<=0) {
			throw new ServiceException("Please provide valid rows and seats",HttpStatus.BAD_REQUEST);
		}
		if(theatreHall.getSeats()>ALPHABETS.length) {
			throw new ServiceException("Seats are exceeding , available size"+ALPHABETS.length,HttpStatus.BAD_REQUEST);
		}
		theatreHall.setLayout(createLayout(theatreHall.getRow(), theatreHall.getSeats()));
		try {
			return theatreHallRepository.save(theatreHall);
		}
		catch(Exception e) {
			throw new ServiceException("Unable to Store Theatre Hall Details",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	public List<TheatreHall> getTheatreHalls()throws ServiceException{
		try{
			return theatreHallRepository.findAll();
		}catch (Exception e) {
			throw new ServiceException("Unable to find Theatre Halls");
		}
	}
	
	public TheatreHall getTheatreHall(String id) throws ServiceException {
		try{
			return theatreHallRepository.findById(id).orElseThrow();
		}catch (Exception e) {
			throw new ServiceException("Unable to find Theatre Hall");
		}
	}
	
	public void deleteTheatreHall(String id) throws ServiceException {
		try {
			theatreHallRepository.deleteById(id);
		}catch (Exception e) {
			throw new ServiceException("Unable to delete Theatre Halls");
		}
		 
	}
	
	private String[][] createLayout(int rows, int cols){
		String[][]  layout=new String[rows][cols] ;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				layout[i][j]=ALPHABETS[i]+""+(i+1);
			}
		}
		return layout;
	}

}
