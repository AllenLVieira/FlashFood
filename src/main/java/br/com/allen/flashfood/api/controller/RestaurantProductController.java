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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class RestaurantProductController {

    private final ProductRepository productRepository;
    private final ProductRegistrationService productService;
    private final RestaurantRegistrationService restaurantService;
    private final ProductModelAssembler productAssembler;
    private final ProductRequestDisassembler productDisassembler;

    @GetMapping
    public List<ProductResponse> getAllProductsByRestaurant(@PathVariable Long restaurantId,
                                                            @RequestParam(required = false) boolean includeInactive) {
        Restaurant restaurant = restaurantService.findRestaurantOrElseThrow(restaurantId);
        
        if (includeInactive) {
            return productAssembler.toCollectionModel(productRepository.findByRestaurant(restaurant));
        } else {
            return productAssembler.toCollectionModel(productRepository.findActiveProductsByRestaurant(restaurant));
        }
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
