package com.skalashynski.spring.microservices.movieinfoservice.web;

import com.skalashynski.spring.microservices.movieinfoservice.MovieInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieInfoController {

  @RequestMapping("/{movieId}")
  public MovieInfo getMovieInfo(@PathVariable String movieId) {
    return new MovieInfo(movieId, "Test name");
  }
}
