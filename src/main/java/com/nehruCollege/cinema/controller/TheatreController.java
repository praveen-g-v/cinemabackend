package com.nehruCollege.cinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.TheatreHall;
import com.nehruCollege.cinema.service.TheatreHallService;

@RestController
@RequestMapping("/api/theatre")
public class TheatreController {
	
	@Autowired
	private TheatreHallService theatreHallService;
	
	
	@PostMapping
	public ResponseEntity<String> addTheatreHall(
			@RequestParam(value="hallname")
			String name,
			@RequestParam(value="location")
			String location,
			@RequestParam(value="rows")
			int rows,
			@RequestParam(value="seats")
			int seats
			
			) throws ServiceException{
		
		TheatreHall hall=new TheatreHall();
		hall.setName(name);
		hall.setLocation(location);
		hall.setRow(rows);
		hall.setSeats(seats);
		theatreHallService.addTheatreHall(hall);
		return new ResponseEntity<String>("Created Sucessfully",HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public List<TheatreHall> getAllTheatreHall() throws ServiceException{
		return theatreHallService.getTheatreHalls();
	}
	
	
	@GetMapping("/{id}")
	public TheatreHall getTheatreHall(@RequestParam(value = "id") String id) throws ServiceException {
		return theatreHallService.getTheatreHall(id);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteTheatreHall(@RequestParam(value = "id") String id) throws ServiceException {
		theatreHallService.deleteTheatreHall(id);
		return new ResponseEntity<String>("Deleted Successfully",HttpStatus.ACCEPTED);
	}
	

}
