package com.nehruCollege.cinema.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nehruCollege.cinema.model.ShowTime;


@Repository
public interface ShowTimeRepository extends MongoRepository<ShowTime, String>{

	List<ShowTime> findByDateBetween(Date startDate, Date endDate);
	
}
