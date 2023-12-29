package br.com.allen.flashfood.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class RestaurantProductPhotoController {

  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void updatePhoto(
      @PathVariable Long restaurantId,
      @PathVariable Long productId,
      @RequestParam MultipartFile file) {}
}
