package com.nehruCollege.cinema.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nehruCollege.cinema.enums.AgeRestriction;
import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.Cast;
import com.nehruCollege.cinema.model.Crew;
import com.nehruCollege.cinema.model.Movie;
import com.nehruCollege.cinema.repository.MovieRepository;
import com.nehruCollege.cinema.service.ImageService;
import com.nehruCollege.cinema.service.MovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	
	@Autowired
	MovieService movieService;
	
	@Autowired
	ImageService imageService;
	
	@PostMapping
	public ResponseEntity<String> addMovie(
			 	@RequestPart("poster") MultipartFile image,
	            @RequestParam("title") String title,
	            @RequestParam("genre") String genre,
	            @RequestParam("duration") String duration,
	            @RequestParam("synopsis") String synopsis,
	            @RequestParam("cast") String[] cast,
	            @RequestParam("crew") String[] crew,
	            @RequestParam("ageRestriction") AgeRestriction ageRestriction,
	            @RequestPart("trailer") String trailer
			)  throws ServiceException {
		Movie movie=new Movie();
		
			
		
		movie.setTitle(title);
		movie.setDuration(Integer.parseInt(duration));
		movie.setGenre(genre);
		if(cast.length<=0) {
			throw new ServiceException("Unable to fimd cast");
		}
		Cast[] newCast=new Cast[cast.length];
		int i=0;
		for(String name:Arrays.asList(cast)) {
			newCast[i]=new Cast();
			newCast[i].setName(name);
			newCast[i].setProfile("UnAvailable");
			i++;
		}
		if(crew.length<=0) {
			throw new ServiceException("Unable to fimd crew");
		}
		Crew[] newCrew =new Crew[crew.length];
		i=0;
		for(String name:Arrays.asList(crew)) {
			newCrew[i]=new Crew();
			newCrew[i].setName(name);
			newCrew[i].setProfile("UnAvailable");
			i++;
		}
		movie.setCast(newCast);
		movie.setCrew(newCrew);
		movie.setSynopsis(synopsis);
		movie.setAgeRestriction(ageRestriction);
		movie.setTrailer(trailer);
		movie.setImageId(imageService.storeImage(image).getImageId());
		movieService.addMovie(movie);
		return new ResponseEntity<String>("sucess",HttpStatus.ACCEPTED);
			
				
		
	}
	
	
	/**
	 * Need to implement get movie Right now I believe it is not required
	 * to delete as well as getting single movie details
	 * @return
	 * @throws ServiceException 
	 */
	@GetMapping
	public ResponseEntity<List<Movie>> getAllMovie() throws ServiceException{
			List<Movie> movies=movieService.getAllMovie();
			return new ResponseEntity<List<Movie>>(movies,HttpStatus.ACCEPTED);
		
		
	}
	
	@GetMapping("/?id")
	public ResponseEntity<Movie> getMovie(@RequestParam("id") String id){
		return new ResponseEntity<Movie>(new Movie(),HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/?id")
	public ResponseEntity<String> deleteMovie(
			@RequestParam("id")String id
			){
		return new ResponseEntity<String>("Failed",HttpStatus.BAD_REQUEST);
	}
	
}
