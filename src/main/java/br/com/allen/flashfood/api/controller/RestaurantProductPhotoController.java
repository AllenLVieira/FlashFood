package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PhotoProductModelAssembler;
import br.com.allen.flashfood.api.model.request.ProductPhotoRequest;
import br.com.allen.flashfood.api.model.response.PhotoProductResponse;
import br.com.allen.flashfood.domain.model.PhotoProduct;
import br.com.allen.flashfood.domain.model.Product;
import br.com.allen.flashfood.domain.service.PhotoProductCatalogService;
import br.com.allen.flashfood.domain.service.ProductRegistrationService;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
@RequiredArgsConstructor
public class RestaurantProductPhotoController {

  private final PhotoProductCatalogService photoProductCatalogService;
  private final ProductRegistrationService productRegistrationService;
  private final PhotoProductModelAssembler photoProductModelAssembler;

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
}
