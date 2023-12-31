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
    String filenameExists = null;

    Optional<PhotoProduct> existingPhotoProduct =
        productRepository.findPhotoById(restaurantId, productId);
    if (existingPhotoProduct.isPresent()) {
      filenameExists = existingPhotoProduct.get().getFilename();
      productRepository.delete(existingPhotoProduct.get());
    }

    photo.setFilename(newFileName);
    PhotoProduct save = productRepository.save(photo);
    productRepository.flush();

    PhotoStorageService.NewPhoto newPhoto =
        PhotoStorageService.NewPhoto.builder()
            .filename(photo.getFilename())
            .inputStream(fileData)
            .build();

    photoStorageService.replaceName(filenameExists, newPhoto);

    return save;
  }
}
