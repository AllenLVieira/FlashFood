package br.com.allen.flashfood.api.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

  @PutMapping
  public void updatePhoto() {}
}
