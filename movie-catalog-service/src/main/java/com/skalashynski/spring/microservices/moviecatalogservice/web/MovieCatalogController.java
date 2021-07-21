package com.skalashynski.spring.microservices.moviecatalogservice.web;

import com.skalashynski.spring.microservices.moviecatalogservice.model.CatalogItem;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

  @GetMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable String userId) {
    return Collections.singletonList(
        new CatalogItem("Transformers", "Test", 1));
  }
}
