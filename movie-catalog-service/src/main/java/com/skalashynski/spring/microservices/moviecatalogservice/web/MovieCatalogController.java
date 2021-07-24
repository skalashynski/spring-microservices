package com.skalashynski.spring.microservices.moviecatalogservice.web;

import com.skalashynski.spring.microservices.moviecatalogservice.model.CatalogItem;
import com.skalashynski.spring.microservices.moviecatalogservice.model.UserRating;
import com.skalashynski.spring.microservices.moviecatalogservice.service.MovieInfoService;
import com.skalashynski.spring.microservices.moviecatalogservice.service.UserRatingInfoService;
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

  @Autowired
  private MovieInfoService movieInfoService;

  @Autowired
  private UserRatingInfoService userRatingInfoService;

  @RequestMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
    UserRating userRating = userRatingInfoService.getUserRating(userId);

    return userRating.getRatings().stream()
        .map(e-> movieInfoService.getCatalogItem(e))
        .collect(Collectors.toList());
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
