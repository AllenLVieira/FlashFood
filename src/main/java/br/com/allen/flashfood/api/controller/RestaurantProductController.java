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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
    value = "/restaurants/{restaurantId}/products",
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(
    name = "Restaurant Products",
    description =
        "Manages the products or menu items for each restaurant in"
            + " the FlashFood application. This controller is responsible for adding new products to restaurant menus,"
            + " updating product information, retrieving details about specific products, and removing products as"
            + " needed. It plays a crucial role in ensuring that restaurant menus are up-to-date, diverse, and"
            + " accurately represent the culinary offerings available to customers.")
public class RestaurantProductController {

  private final ProductRepository productRepository;
  private final ProductRegistrationService productService;
  private final RestaurantRegistrationService restaurantService;
  private final ProductModelAssembler productAssembler;
  private final ProductRequestDisassembler productDisassembler;

  @GetMapping
  @Operation(description = "Get all the products of a restaurant in the Flashfood application.")
  public List<ProductResponse> getAllProductsByRestaurant(
      @PathVariable Long restaurantId, @RequestParam(required = false) boolean includeInactive) {
    Restaurant restaurant = restaurantService.findRestaurantOrElseThrow(restaurantId);

    if (includeInactive) {
      return productAssembler.toCollectionModel(productRepository.findByRestaurant(restaurant));
    } else {
      return productAssembler.toCollectionModel(
          productRepository.findActiveProductsByRestaurant(restaurant));
    }
  }

  @GetMapping("/{productId}")
  @Operation(description = "Get specific product from restaurant in the Flashfood application.")
  public ProductResponse getByRestaurantAndProduct(
      @PathVariable Long restaurantId, @PathVariable Long productId) {
    Product product = productService.findProductOrElseThrow(restaurantId, productId);
    return productAssembler.toModel(product);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(description = "Add new product to restaurant in the Flashfood application.")
  public ProductResponse addProduct(
      @PathVariable Long restaurantId, @RequestBody @Valid ProductRequest productRequest) {
    Restaurant restaurant = restaurantService.findRestaurantOrElseThrow(restaurantId);
    Product product = productDisassembler.toDomainObject(productRequest);
    product.setRestaurant(restaurant);
    product = productService.saveProduct(product);
    return productAssembler.toModel(product);
  }

  @PutMapping("/{productId}")
  @Operation(description = "Update a product from restaurant id in the Flashfood application.")
  public ProductResponse updateProduct(
      @PathVariable Long restaurantId,
      @PathVariable Long productId,
      @RequestBody @Valid ProductRequest productRequest) {
    Product actualProduct = productService.findProductOrElseThrow(restaurantId, productId);
    productDisassembler.copyToDomainObject(productRequest, actualProduct);
    actualProduct = productService.saveProduct(actualProduct);
    return productAssembler.toModel(actualProduct);
  }
}
