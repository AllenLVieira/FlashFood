package br.com.allen.flashfood.api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import br.com.allen.flashfood.api.assembler.ProductModelAssembler;
import br.com.allen.flashfood.api.assembler.ProductRequestDisassembler;
import br.com.allen.flashfood.api.model.request.ProductRequest;
import br.com.allen.flashfood.api.model.response.ProductResponse;
import br.com.allen.flashfood.domain.model.*;
import br.com.allen.flashfood.domain.repository.ProductRepository;
import br.com.allen.flashfood.domain.service.ProductRegistrationService;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RestaurantProductController.class})
@ExtendWith(SpringExtension.class)
class RestaurantProductControllerTest {

  private static final ObjectMapper objectMapper = new ObjectMapper();
  State state = new State();
  City city = new City();
  Address address = new Address();
  Cuisine cuisine = new Cuisine();
  Restaurant restaurant = new Restaurant();
  Product product = new Product();
  List<ProductResponse> activeProducts = new ArrayList<>();
  List<ProductResponse> allProducts = new ArrayList<>();
  ProductResponse productResponse1 = new ProductResponse();
  ProductResponse productResponse2 = new ProductResponse();

  @Autowired private RestaurantProductController underTest;

  @MockBean private ProductModelAssembler productModelAssembler;

  @MockBean private ProductRegistrationService productRegistrationService;

  @MockBean private ProductRepository productRepository;

  @MockBean private ProductRequestDisassembler productRequestDisassembler;

  @MockBean private RestaurantRegistrationService restaurantRegistrationService;

  @BeforeEach
  void setUp() {
    state.setId(123L);
    state.setName("São Paulo");

    city.setId(123L);
    city.setName("São Paulo");
    city.setState(state);

    address.setCity(city);
    address.setComplement("Complement");
    address.setDistrict("District");
    address.setNumber("42");
    address.setStreet("Street");
    address.setZipCode("21654");

    cuisine.setId(123L);
    cuisine.setName("Name");
    cuisine.setRestaurant(new ArrayList<>());

    restaurant.setActive(true);
    restaurant.setAddress(address);
    restaurant.setCuisine(cuisine);
    restaurant.setFreightRate(BigDecimal.valueOf(42L));
    restaurant.setId(123L);
    restaurant.setManagers(new HashSet<>());
    restaurant.setName("Name");
    restaurant.setOpenStatus(true);
    restaurant.setPaymentMethod(new HashSet<>());
    restaurant.setProducts(new ArrayList<>());
    restaurant.setRegistrationDate(null);
    restaurant.setUpdateDate(null);

    product.setActive(true);
    product.setDescription("Description");
    product.setId(1L);
    product.setName("Product 1");
    product.setPrice(BigDecimal.valueOf(10L));
    product.setRestaurant(restaurant);

    productResponse1.setId(1L);
    productResponse1.setName("Product 1");
    productResponse1.setActive(true);
    productResponse1.setPrice(BigDecimal.valueOf(10L));
    productResponse1.setDescription("Description");
    productResponse2.setId(2L);
    productResponse2.setName("Product 2");
    productResponse2.setActive(false);
    productResponse2.setPrice(BigDecimal.valueOf(99L));
    productResponse2.setDescription("Description 2");
    activeProducts.add(productResponse1);
    allProducts.add(productResponse1);
    allProducts.add(productResponse2);
  }

  /** Method under test: {@link RestaurantProductController#addProduct(Long, ProductRequest)} */
  @Test
  void testAddProduct() throws Exception {
    when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant);

    ProductRequest productRequest = new ProductRequest();
    productRequest.setActive(true);
    productRequest.setDescription("Description 3");
    productRequest.setName("Product 3");
    productRequest.setPrice(BigDecimal.valueOf(42L));

    Product product3 = new Product();
    product3.setId(3L);
    product3.setName("Product 3");
    product3.setActive(true);
    product3.setDescription("Description 3");
    product3.setPrice(BigDecimal.valueOf(42L));
    product3.setRestaurant(restaurant);

    ProductResponse productResponse3 = new ProductResponse();
    productResponse3.setId(3L);
    productResponse3.setName("Product 3");
    productResponse3.setActive(true);
    productResponse3.setDescription("Description 3");
    productResponse3.setPrice(BigDecimal.valueOf(42L));

    String json = objectMapper.writeValueAsString(productRequest);

    MockHttpServletRequestBuilder contentTypeResult =
        MockMvcRequestBuilders.post("/restaurants/{restaurantId}/products", 123L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json);

    when(productRegistrationService.saveProduct(any(Product.class))).thenReturn(product3);
    when(productModelAssembler.toModel(any(Product.class))).thenReturn(productResponse3);
    when(productRequestDisassembler.toDomainObject(any(ProductRequest.class))).thenReturn(product3);

    MockMvcBuilders.standaloneSetup(underTest)
        .build()
        .perform(contentTypeResult)
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(jsonPath("$.name", Matchers.is("Product 3")));
  }

  /**
   * Method under test: {@link RestaurantProductController#getAllProductsByRestaurant(Long,
   * boolean)}
   */
  @Test
  void testGetAllActiveProductsByRestaurant() throws Exception {
    when(productRepository.findActiveProductsByRestaurant(any())).thenReturn(new ArrayList<>());
    when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant);
    when(productModelAssembler.toCollectionModel(any())).thenReturn(activeProducts);

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/restaurants/{restaurantId}/products", 123L);
    MockMvcBuilders.standaloneSetup(underTest)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].name", Matchers.is("Product 1")));
  }

  /**
   * Method under test: {@link RestaurantProductController#getAllProductsByRestaurant(Long,
   * boolean)}
   */
  @Test
  void testGetAllProductsByRestaurant2() throws Exception {
    when(productRepository.findByRestaurant(any())).thenReturn(new ArrayList<>());
    when(productRepository.findActiveProductsByRestaurant(any())).thenReturn(new ArrayList<>());
    when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant);
    when(productModelAssembler.toCollectionModel(any())).thenReturn(allProducts);

    MockHttpServletRequestBuilder getResult =
        MockMvcRequestBuilders.get("/restaurants/{restaurantId}/products", 123L);
    MockHttpServletRequestBuilder requestBuilder =
        getResult.param("includeInactive", String.valueOf(true));
    MockMvcBuilders.standaloneSetup(underTest)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name", Matchers.is("Product 1")))
        .andExpect(jsonPath("$[1].name", Matchers.is("Product 2")));
  }

  /**
   * Method under test: {@link RestaurantProductController#getByRestaurantAndProduct(Long, Long)}
   */
  @Test
  void testGetByRestaurantAndProduct() throws Exception {
    when(productRegistrationService.findProductOrElseThrow(any(), any())).thenReturn(product);
    when(productModelAssembler.toModel(any())).thenReturn(productResponse1);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/restaurants/{restaurantId}/products/{productId}", 123L, 1L);
    MockMvcBuilders.standaloneSetup(underTest)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(jsonPath("$.name", Matchers.is("Product 1")))
        .andExpect(jsonPath("$.description", Matchers.is("Description")));
  }

  @Test
  void updateProduct() throws Exception {
    ProductRequest request = new ProductRequest();
    request.setActive(true);
    request.setDescription("Description Updated");
    request.setName("Product 1 - Updated");
    request.setPrice(BigDecimal.valueOf(9L));

    Product updatedProduct = new Product();
    updatedProduct.setActive(true);
    updatedProduct.setDescription("Description Updated");
    updatedProduct.setId(1L);
    updatedProduct.setName("Product 1 - Updated");
    updatedProduct.setPrice(BigDecimal.valueOf(9L));
    updatedProduct.setRestaurant(restaurant);

    ProductResponse productResponseUpdated = new ProductResponse();
    productResponseUpdated.setId(1L);
    productResponseUpdated.setActive(true);
    productResponseUpdated.setDescription("Description Updated");
    productResponseUpdated.setName("Product 1 - Updated");

    String json = objectMapper.writeValueAsString(request);

    when(productRegistrationService.findProductOrElseThrow(any(), any())).thenReturn(product);
    doNothing().when(productRequestDisassembler).copyToDomainObject(request, product);
    when(productRegistrationService.saveProduct(any(Product.class))).thenReturn(updatedProduct);
    when(productModelAssembler.toModel(any(Product.class))).thenReturn(productResponseUpdated);

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/restaurants/{restaurantId}/products/{productId}", 123L, 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json);
    MockMvcBuilders.standaloneSetup(underTest)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(jsonPath("$.name", Matchers.is("Product 1 - Updated")))
        .andExpect(jsonPath("$.description", Matchers.is("Description Updated")));
  }
}
