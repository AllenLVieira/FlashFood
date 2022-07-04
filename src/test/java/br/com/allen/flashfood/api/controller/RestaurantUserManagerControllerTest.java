package br.com.allen.flashfood.api.controller;

import br.com.allen.flashfood.api.assembler.UserModelAssembler;
import br.com.allen.flashfood.api.model.response.UserResponse;
import br.com.allen.flashfood.domain.model.*;
import br.com.allen.flashfood.domain.service.RestaurantRegistrationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration(classes = {RestaurantUserManagerController.class})
@ExtendWith(SpringExtension.class)
class RestaurantUserManagerControllerTest {
    @Autowired
    private RestaurantUserManagerController underTest;

    @MockBean
    private RestaurantRegistrationService restaurantRegistrationService;

    @MockBean
    private UserModelAssembler userModelAssembler;

    /**
     * Method under test: {@link RestaurantUserManagerController#unlinkManager(Long, Long)}
     */
    @Test
    void testUnlinkManager() throws Exception {
        doNothing().when(restaurantRegistrationService).unlinkManager(any(), any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/restaurants/{restaurantId}/managers/{userId}", 123L, 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(underTest)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    /**
     * Method under test: {@link RestaurantUserManagerController#getAllManagers(Long)}
     */
    @Test
    void testGetAllManagers() throws Exception {
        State state = new State();
        state.setId(123L);
        state.setName("Name");

        City city = new City();
        city.setId(123L);
        city.setName("Name");
        city.setState(state);

        Address address = new Address();
        address.setCity(city);
        address.setComplement("Complement");
        address.setDistrict("District");
        address.setNumber("42");
        address.setStreet("Street");
        address.setZipCode("21654");

        Cuisine cuisine = new Cuisine();
        cuisine.setId(123L);
        cuisine.setName("Name");
        cuisine.setRestaurant(new ArrayList<>());

        Restaurant restaurant = new Restaurant();
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

        UserResponse userResponse = new UserResponse();
        userResponse.setId(123L);
        userResponse.setName("Allen");
        userResponse.setEmail("allenvieira96@gmail.com");

        List<UserResponse> result = new ArrayList<>();
        result.add(userResponse);

        when(restaurantRegistrationService.findRestaurantOrElseThrow(any())).thenReturn(restaurant);
        when(userModelAssembler.toCollectionModel(any())).thenReturn(result);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/restaurants/{restaurantId}/managers", 123L);
        MockMvcBuilders.standaloneSetup(underTest)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("Allen")));
    }

    /**
     * Method under test: {@link RestaurantUserManagerController#linkManager(Long, Long)}
     */
    @Test
    void testLinkManager() throws Exception {
        doNothing().when(restaurantRegistrationService).linkManager(any(), any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/restaurants/{restaurantId}/managers/{userId}", 123L, 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(underTest)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

