package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PhotoProductModelAssembler;
import br.com.allen.flashfood.api.controller.openapi.RestaurantProductPhotoControllerOpenApi;
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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
@RequiredArgsConstructor
public class RestaurantProductPhotoController implements RestaurantProductPhotoControllerOpenApi {

  private final PhotoProductCatalogService photoProductCatalogService;
  private final ProductRegistrationService productRegistrationService;
  private final PhotoProductModelAssembler photoProductModelAssembler;
  private final PhotoStorageService photoStorageService;

  @PutMapping(
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
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
  public ResponseEntity<?> getPhoto(
      @PathVariable Long restaurantId,
      @PathVariable Long productId,
      @RequestHeader(name = "Accept") String acceptHeader)
      throws HttpMediaTypeNotAcceptableException {

    List<MediaType> acceptableMediaTypes = MediaType.parseMediaTypes(acceptHeader);

    try {
      PhotoProduct photo = photoProductCatalogService.findOrElseThrow(restaurantId, productId);
      MediaType mediaTypePhoto = MediaType.parseMediaType(photo.getContentType());
      ensureMediaTypeCompatibility(mediaTypePhoto, acceptableMediaTypes);

      PhotoStorageService.RetrievedPhoto inputStream =
          photoStorageService.retrieve(photo.getFilename());

      if (inputStream.urlExists()) {
        return ResponseEntity.status(HttpStatus.FOUND)
            .header(HttpHeaders.LOCATION, inputStream.getUrl())
            .build();
      } else {
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(new InputStreamResource(inputStream.getInputStream()));
      }

    } catch (EntityNotFoundedException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void remove(@PathVariable Long restaurantId, @PathVariable Long productId) {
    photoProductCatalogService.remove(restaurantId, productId);
  }

  /**
   * Ensures that the given MediaType is compatible with any of the acceptable MediaTypes.
   *
   * @param mediaType the MediaType to check.
   * @param acceptableMediaTypes the list of acceptable MediaTypes.
   * @throws HttpMediaTypeNotAcceptableException if the given MediaType is not compatible.
   */
  private void ensureMediaTypeCompatibility(
      MediaType mediaType, List<MediaType> acceptableMediaTypes)
      throws HttpMediaTypeNotAcceptableException {
    boolean isCompatible =
        acceptableMediaTypes.stream()
            .anyMatch(acceptMediaType -> acceptMediaType.isCompatibleWith(mediaType));
    if (!isCompatible) {
      throw new HttpMediaTypeNotAcceptableException(
          "MediaType "
              + mediaType
              + " is not acceptable. Acceptable types: "
              + acceptableMediaTypes);
    }
  }
}
