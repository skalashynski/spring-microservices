package com.skalashynski.spring.microservices.ratingsdataservice.web;

import com.skalashynski.spring.microservices.ratingsdataservice.model.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingsDataController {

  @RequestMapping("/{movieId}")
  public Rating getRating(@PathVariable String movieId) {
    return new Rating(movieId, 4);
  }

}
