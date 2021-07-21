package com.skalashynski.spring.microservices.moviecatalogservice.web;

import com.skalashynski.spring.microservices.moviecatalogservice.model.CatalogItem;
import com.skalashynski.spring.microservices.moviecatalogservice.model.Movie;
import com.skalashynski.spring.microservices.moviecatalogservice.model.UserRating;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    UserRating userRating = restTemplate
        .getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);

    return userRating.getRatings().stream()
        .map(rating -> {
          Movie movie = restTemplate
              .getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
          return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        })
        .collect(Collectors.toList());
  }


  /*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/
}
