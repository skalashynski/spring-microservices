package com.skalashynski.spring.microservices.moviecatalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rating {
  private String movieId;
  private int rating;
}
