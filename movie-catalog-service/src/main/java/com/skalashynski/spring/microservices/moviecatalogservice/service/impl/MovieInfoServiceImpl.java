package com.skalashynski.spring.microservices.moviecatalogservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.skalashynski.spring.microservices.moviecatalogservice.model.CatalogItem;
import com.skalashynski.spring.microservices.moviecatalogservice.model.Movie;
import com.skalashynski.spring.microservices.moviecatalogservice.model.Rating;
import com.skalashynski.spring.microservices.moviecatalogservice.service.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoServiceImpl implements MovieInfoService {

  @Autowired
  private RestTemplate restTemplate;

  @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
      threadPoolKey = "moveInfoPool",
      threadPoolProperties = {
          @HystrixProperty(name = "coreSize", value = "20"),
          @HystrixProperty(name = "maxQueueSize", value = "10")
      }
  )
  public CatalogItem getCatalogItem(Rating rating) {
    Movie movie = restTemplate
        .getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
  }

  private CatalogItem getFallbackCatalogItem(Rating rating) {
    return new CatalogItem("Movie is not found", "", rating.getRating());
  }
}
