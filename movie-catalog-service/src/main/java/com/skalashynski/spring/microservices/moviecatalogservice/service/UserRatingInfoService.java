package com.skalashynski.spring.microservices.moviecatalogservice.service;

import com.skalashynski.spring.microservices.moviecatalogservice.model.UserRating;

public interface UserRatingInfoService {
  UserRating getUserRating(String userId);
}
