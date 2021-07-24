package com.skalashynski.spring.microservices.moviecatalogservice.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.skalashynski.spring.microservices.moviecatalogservice.model.CatalogItem;
import com.skalashynski.spring.microservices.moviecatalogservice.model.Movie;
import com.skalashynski.spring.microservices.moviecatalogservice.model.Rating;
import com.skalashynski.spring.microservices.moviecatalogservice.model.UserRating;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private WebClient.Builder webClientBuilder;

  @RequestMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
    UserRating userRating = getUserRating(userId);

    return userRating.getRatings().stream()
        .map(this::getCatalogItem)
        .collect(Collectors.toList());
  }

  @HystrixCommand(fallbackMethod = "getFallbackUserRating")
  private UserRating getUserRating(@PathVariable("userId") String userId) {
    return restTemplate
        .getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
  }

  @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
  private CatalogItem getCatalogItem(Rating rating) {
    Movie movie = restTemplate
        .getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
  }

  private UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
    return new UserRating(userId, Arrays.asList(new Rating("0", 0)));
  }

  private CatalogItem getFallbackCatalogItem(Rating rating) {
    return new CatalogItem("Movie is not found", "", rating.getRating());
  }


  /*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/

  private List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
    return Arrays.asList(new CatalogItem("No movie", "", 0));
  }
}
