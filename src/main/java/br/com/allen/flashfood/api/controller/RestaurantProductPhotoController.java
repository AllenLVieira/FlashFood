package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PhotoProductModelAssembler;
import br.com.allen.flashfood.api.model.request.ProductPhotoRequest;
import br.com.allen.flashfood.api.model.response.PhotoProductResponse;
import br.com.allen.flashfood.domain.exception.EntityNotFoundedException;
import br.com.allen.flashfood.domain.model.PhotoProduct;
import br.com.allen.flashfood.domain.model.Product;
import br.com.allen.flashfood.domain.service.PhotoProductCatalogService;
import br.com.allen.flashfood.domain.service.PhotoStorageService;
import br.com.allen.flashfood.domain.service.ProductRegistrationService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
@RequiredArgsConstructor
public class RestaurantProductPhotoController {

  private final PhotoProductCatalogService photoProductCatalogService;
  private final ProductRegistrationService productRegistrationService;
  private final PhotoProductModelAssembler photoProductModelAssembler;
  private final PhotoStorageService photoStorageService;

  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public PhotoProductResponse updatePhoto(
      @PathVariable Long restaurantId,
      @PathVariable Long productId,
      @Valid ProductPhotoRequest productPhotoRequest)
      throws IOException {
    var file = productPhotoRequest.getFile();
    var fileSize = file.getSize();
    var contentType = file.getContentType();
    var fileName = file.getOriginalFilename();

    Product product = productRegistrationService.findProductOrElseThrow(restaurantId, productId);
    PhotoProduct photo = new PhotoProduct();
    photo.setProduct(product);
    photo.setDescription(productPhotoRequest.getDescription());
    photo.setContentType(contentType);
    photo.setFilesize(fileSize);
    photo.setFilename(fileName);

    PhotoProduct savedPhoto = photoProductCatalogService.save(photo, file.getInputStream());

    return photoProductModelAssembler.toModel(savedPhoto);
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public PhotoProductResponse find(@PathVariable Long restaurantId, @PathVariable Long productId) {
    PhotoProduct photo = photoProductCatalogService.findOrElseThrow(restaurantId, productId);
    return photoProductModelAssembler.toModel(photo);
  }

  @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
  public ResponseEntity<InputStreamResource> getPhoto(
      @PathVariable Long restaurantId, @PathVariable Long productId) {
    try {
      PhotoProduct photo = photoProductCatalogService.findOrElseThrow(restaurantId, productId);
      InputStream inputStream = photoStorageService.retrieve(photo.getFilename());
      return ResponseEntity.ok()
          .contentType(MediaType.IMAGE_JPEG)
          .body(new InputStreamResource(inputStream));
    } catch (EntityNotFoundedException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
