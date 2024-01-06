package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.exceptionhandler.ApiError;
import br.com.allen.flashfood.api.model.request.CityRequest;
import br.com.allen.flashfood.api.model.response.CityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(
    name = "City",
    description =
        "Operations related to cities in the FlashFood application. This controller handles the management of city"
            + " data, including the creation of new city entries, retrieval of city details, updating existing"
            + " city data, and deletion of cities. It's integral for managing geographical and locational"
            + " information within the application.")
public interface CityControllerOpenApi {

  @Operation(description = "Get all the cities in the Flashfood application.")
  List<CityResponse> getAllCity();

  @Operation(description = "Get a city by Id in the Flashfood application.")
  @ApiResponse(
      responseCode = "400",
      description = "Invalid City ID",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "City not found",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "200",
      description = "OK",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = CityResponse.class)))
  CityResponse getCityById(Long cityId);

  @Operation(description = "Add a new City to Flashfood application.")
  CityResponse addCity(CityRequest cityRequest);

  @Operation(description = "Update a city based on an Id in the Flashfood application.")
  CityResponse updateCity(Long cityId, CityRequest cityRequest);

  @Operation(description = "Delete a specific city by an Id in the Flashfood application.")
  void deleteCity(Long cityId);
}
