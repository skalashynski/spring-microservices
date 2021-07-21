package com.skalashynski.spring.microservices.movieinfoservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieInfo {
  private String movieId;
  private String name;
}
