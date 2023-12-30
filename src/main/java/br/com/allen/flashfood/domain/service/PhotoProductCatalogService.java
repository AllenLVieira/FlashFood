package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.model.PhotoProduct;
import br.com.allen.flashfood.domain.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.io.InputStream;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoProductCatalogService {

  private final ProductRepository productRepository;
  private final PhotoStorageService photoStorageService;

  @Transactional
  public PhotoProduct save(PhotoProduct photo, InputStream fileData) {
    Long restaurantId = photo.getRestaurantId();
    Long productId = photo.getProduct().getId();
    String newFileName = photoStorageService.generateFileName(photo.getFilename());

    Optional<PhotoProduct> existingPhotoProduct =
        productRepository.findPhotoById(restaurantId, productId);
    existingPhotoProduct.ifPresent(productRepository::delete);

    photo.setFilename(newFileName);
    PhotoProduct save = productRepository.save(photo);
    productRepository.flush();

    PhotoStorageService.NewPhoto newPhoto =
        PhotoStorageService.NewPhoto.builder()
            .filename(photo.getFilename())
            .inputStream(fileData)
            .build();
    photoStorageService.store(newPhoto);

    return save;
  }
}
