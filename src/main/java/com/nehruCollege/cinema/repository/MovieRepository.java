package com.nehruCollege.cinema.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nehruCollege.cinema.model.Movie;

@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
    // You can add custom query methods here
    List<Movie> findByTitleContainingIgnoreCase(String title);
}