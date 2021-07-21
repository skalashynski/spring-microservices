package com.skalashynski.spring.microservices.ratingsdataservice.web;

import com.skalashynski.spring.microservices.ratingsdataservice.model.Rating;
import com.skalashynski.spring.microservices.ratingsdataservice.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataController {

  @RequestMapping("/movies/{movieId}")
  public Rating getMovieRating(@PathVariable String movieId) {
    return new Rating(movieId, 4);
  }

  @RequestMapping("/user/{userId}")
  public UserRating getUserRatings(@PathVariable("userId") String userId) {
    UserRating userRating = new UserRating();
    userRating.initData(userId);
    return userRating;

  }

}
