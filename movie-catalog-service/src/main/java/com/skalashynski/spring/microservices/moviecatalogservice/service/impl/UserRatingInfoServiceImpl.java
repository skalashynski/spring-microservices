package com.skalashynski.spring.microservices.moviecatalogservice.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.skalashynski.spring.microservices.moviecatalogservice.model.Rating;
import com.skalashynski.spring.microservices.moviecatalogservice.model.UserRating;
import com.skalashynski.spring.microservices.moviecatalogservice.service.UserRatingInfoService;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class UserRatingInfoServiceImpl implements UserRatingInfoService {

  @Autowired
  private RestTemplate restTemplate;

  @HystrixCommand(fallbackMethod = "getFallbackUserRating")
  public UserRating getUserRating(String userId) {
    return restTemplate
        .getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
  }


  private UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
    return new UserRating(userId, Arrays.asList(new Rating("0", 0)));
  }


}
