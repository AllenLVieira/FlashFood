package br.com.allen.flashfood.api;

import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@UtilityClass
public class ResourceUriUtils {
  public static void addUriToResponseHeader(Object resourceId) {
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/{id}")
            .buildAndExpand(resourceId)
            .toUri();
    HttpServletResponse response =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    response.setHeader(HttpHeaders.LOCATION, uri.toString());
  }
}
