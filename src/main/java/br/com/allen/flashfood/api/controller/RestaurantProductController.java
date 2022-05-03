package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.ProductModelAssembler;
import br.com.allen.flashfood.api.assembler.ProductRequestDisassembler;
import br.com.allen.flashfood.api.model.request.ProductRequest;
import br.com.allen.flashfood.api.model.response.ProductResponse;
import br.com.allen.flashfood.domain.model.Product;
import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.ProductRepository;
import br.com.allen.flashfood.domain.service.ProductRegistrationService;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRegistrationService productService;

    @Autowired
    private RestaurantRegistrationService restaurantService;

    @Autowired
    private ProductModelAssembler productAssembler;

    @Autowired
    private ProductRequestDisassembler productDisassembler;

    @GetMapping
    public List<ProductResponse> getAllProductsByRestaurant(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.findRestaurantOrElseThrow(restaurantId);
        List<Product> allProducts = productRepository.findByRestaurant(restaurant);
        return productAssembler.toCollectionModel(allProducts);
    }

    @GetMapping("/{productId}")
    public ProductResponse getByRestaurantAndProduct(@PathVariable Long restaurantId,
                                                     @PathVariable Long productId) {
        Product product = productService.findProductOrElseThrow(restaurantId, productId);
        return productAssembler.toModel(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProduct(@PathVariable Long restaurantId,
                                      @RequestBody @Valid ProductRequest productRequest) {
        Restaurant restaurant = restaurantService.findRestaurantOrElseThrow(restaurantId);
        Product product = productDisassembler.toDomainObject(productRequest);
        product.setRestaurant(restaurant);
        product = productService.saveProduct(product);
        return productAssembler.toModel(product);
    }

    @PutMapping("/{productId}")
    public ProductResponse updateProduct(@PathVariable Long restaurantId,
                                         @PathVariable Long productId,
                                         @RequestBody @Valid ProductRequest productRequest) {
        Product actualProduct = productService.findProductOrElseThrow(restaurantId, productId);
        productDisassembler.copyToDomainObject(productRequest, actualProduct);
        actualProduct = productService.saveProduct(actualProduct);
        return productAssembler.toModel(actualProduct);
    }
}
