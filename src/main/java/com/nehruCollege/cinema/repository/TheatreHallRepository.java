package com.nehruCollege.cinema.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nehruCollege.cinema.model.TheatreHall;


@Repository
public interface TheatreHallRepository extends MongoRepository<TheatreHall, String>{

}
