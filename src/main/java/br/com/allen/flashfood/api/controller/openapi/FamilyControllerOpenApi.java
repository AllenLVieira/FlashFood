package br.com.allen.flashfood.api.controller.openapi;

import br.com.allen.flashfood.api.exceptionhandler.ApiError;
import br.com.allen.flashfood.api.model.request.FamilyRequest;
import br.com.allen.flashfood.api.model.response.FamilyResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@Tag(
    name = "Family",
    description =
        "Manages all operations related to family entities in the FlashFood application."
            + " This controller is responsible for handling family data, including the creation of new family entries,"
            + " retrieval and updating of family details, and deletion of family records. It's essential for maintaining"
            + " family-related information, potentially important for user profiles or food preferences.")
public interface FamilyControllerOpenApi {

  @Operation(description = "Get a family by Id in the Flashfood application.")
  @ApiResponse(
      responseCode = "400",
      description = "Invalid Family ID",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Family not found",
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
              schema = @Schema(implementation = FamilyResponse.class)))
  FamilyResponse findFamilyById(@PathVariable Long groupId);

  @Operation(description = "Get all the families in the Flashfood application.")
  List<FamilyResponse> getAllGroups();

  @Operation(description = "Add a new Family in the Flashfood application.")
  FamilyResponse addFamily(@RequestBody @Valid FamilyRequest familyRequest);

  @Operation(description = "Update a Family in the Flashfood application.")
  @ApiResponse(
      responseCode = "404",
      description = "Family not found",
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
              schema = @Schema(implementation = FamilyResponse.class)))
  FamilyResponse updateFamily(
      @PathVariable Long groupId, @RequestBody @Valid FamilyRequest familyRequest);

  @Operation(description = "Delete a family by Id in the Flashfood application.")
  @ApiResponse(
      responseCode = "404",
      description = "Family not found",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "204",
      description = "OK",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = FamilyResponse.class)))
  void deleteFamily(@PathVariable Long groupId);
}
