package br.com.allen.flashfood.domain.service;

import br.com.allen.flashfood.domain.exception.ProductNotFoundException;
import br.com.allen.flashfood.domain.model.Product;
import br.com.allen.flashfood.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductRegistrationService {
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product findProductOrElseThrow(Long restaurantId, Long productId) {
        return productRepository.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
    }
}
