package com.nehruCollege.cinema.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.Movie;
import com.nehruCollege.cinema.repository.ImageRepository;
import com.nehruCollege.cinema.repository.MovieRepository;

@Service
public class MovieService  {

	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ImageService imageService;
	
	public Movie addMovie(Movie movie)throws ServiceException{
		if(movie.getCast().length>0) {
			if(movie.getCrew().length>0) {
				if(movie.getDuration()>0){
					if(movie.getGenre().length()>0) {
						if(movie.getSynopsis().length()>0){
							if(movie.getImageId().length()>0) {
								return movieRepository.save(movie);
							}
							else {
								throw new ServiceException("Unable to find ImageId");
							}
						}
						else {
							throw new ServiceException("Unable to find Synopsis");
						}
					}
					else {
						throw new ServiceException("Unable to find Genre");
					}
				}
				else {
					throw new ServiceException("Unable to find Duration");
				}
			}
			else {
				throw new ServiceException("Unable to find Crew");
			}
		}
		else {
			throw new ServiceException("Unable to find Cast");
		}
		
	}
	
	
	public List<Movie> getAllMovie() throws ServiceException {
		try {
			return movieRepository.findAll();
		}catch (Exception e) {
			throw new ServiceException("Unable to find all Movie");
		}
		
	}
	
	public Movie getMovie(String id)throws ServiceException{
			return movieRepository.findById(id).orElseThrow(()-> new ServiceException("Unable to find the movie with id :"+id));
		
	}
	
	public Movie deleteMovie(String id)throws ServiceException{
		Movie movie=movieRepository.findById(id).orElseThrow(()-> new ServiceException("Unable to find Movie"));
		imageService.deleteImage(movie.getImageId());
		movieRepository.deleteById(id);
		return movie;
	}
}
