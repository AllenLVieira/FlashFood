package br.com.allen.flashfood.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

  @Override
  public void serialize(
      Page<?> objects, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
      throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeObjectField("content", objects.getContent());
    jsonGenerator.writeNumberField("size", objects.getSize());
    jsonGenerator.writeNumberField("totalElements", objects.getTotalElements());
    jsonGenerator.writeNumberField("totalPages", objects.getTotalPages());
    jsonGenerator.writeNumberField("number", objects.getNumber());
    jsonGenerator.writeEndObject();
  }
}
