package com.skalashynski.spring.microservices.moviecatalogservice.service;

import com.skalashynski.spring.microservices.moviecatalogservice.model.CatalogItem;
import com.skalashynski.spring.microservices.moviecatalogservice.model.Rating;


public interface MovieInfoService {
  CatalogItem getCatalogItem(Rating rating);
}
