package br.com.allen.flashfood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "API Error")
public class ApiError {
  @Schema(example = "400", description = "HTTP Status")
  private Integer status;

  @Schema(example = "https://www.flashfood.com.br/invalid-data")
  private String type;

  @Schema(example = "Invalid data")
  private String title;

  @Schema(example = "One or more fields are invalid. Fill it correctly and try again.")
  private String detail;

  @Schema(example = "One or more fields are invalid. Fill it correctly and try again.")
  private String userMessage;

  @Schema(example = "2024-01-06T18:59:20.4561732Z", description = "ISO Timestamp")
  private OffsetDateTime timestamp;

  @Schema(description = "Fields or objects that caused the error (optional)")
  private List<Field> fields;

  @Getter
  @Builder
  @Schema(name = "Field Error")
  public static class Field {
    @Schema(example = "name")
    private String name;

    @Schema(example = "The name is mandatory")
    private String userMessage;
  }
}
