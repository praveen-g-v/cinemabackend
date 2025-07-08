package com.nehruCollege.cinema.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.nehruCollege.cinema.model.Image;


@Repository
public interface ImageRepository extends MongoRepository<Image, String> {

}
