package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.model.PhotoProduct;
import br.com.allen.flashfood.domain.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoProductCatalogService {

  private final ProductRepository productRepository;

  @Transactional
  public PhotoProduct save(PhotoProduct photo) {
    Long restaurantId = photo.getRestaurantId();
    Long productId = photo.getProduct().getId();

    Optional<PhotoProduct> existingPhotoProduct =
        productRepository.findPhotoById(restaurantId, productId);
    existingPhotoProduct.ifPresent(productRepository::delete);

    return productRepository.save(photo);
  }
}
