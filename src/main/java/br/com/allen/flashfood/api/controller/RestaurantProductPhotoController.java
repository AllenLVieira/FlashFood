package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.PhotoProductModelAssembler;
import br.com.allen.flashfood.api.model.request.ProductPhotoRequest;
import br.com.allen.flashfood.api.model.response.PhotoProductResponse;
import br.com.allen.flashfood.domain.model.PhotoProduct;
import br.com.allen.flashfood.domain.model.Product;
import br.com.allen.flashfood.domain.service.PhotoProductCatalogService;
import br.com.allen.flashfood.domain.service.ProductRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
@RequiredArgsConstructor
public class RestaurantProductPhotoController {

  private static final String FILE_STORAGE_PATH = "/Users/allen/Desktop/catalog";
  private final PhotoProductCatalogService photoProductCatalogService;
  private final ProductRegistrationService productRegistrationService;
  private final PhotoProductModelAssembler photoProductModelAssembler;

  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public PhotoProductResponse updatePhoto(
      @PathVariable Long restaurantId,
      @PathVariable Long productId,
      @Valid ProductPhotoRequest productPhotoRequest) {
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

    PhotoProduct savedPhoto = photoProductCatalogService.save(photo);

    return photoProductModelAssembler.toModel(savedPhoto);

    /*if (fileSize > 10485760) {
      return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
          .body("File size exceeds the allowed limit of 10 MB.");
    }

    if (contentType != null && !contentType.startsWith("image/")) {
      return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
          .body("Only image files are supported.");
    } else if (contentType == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing or invalid content type.");
    }

    var fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    fileName = Paths.get(fileName).getFileName().toString();
    var photoFile = Paths.get(FILE_STORAGE_PATH, fileName);
    try {
      Files.createDirectories(photoFile.getParent());
      file.transferTo(photoFile);
    } catch (IOException e) {
      System.out.println("Error saving photo: " + e.getMessage());
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to sabe Photo. Please try again later.");
    }
    return ResponseEntity.ok("Photo updated successfully.");*/
  }
}
