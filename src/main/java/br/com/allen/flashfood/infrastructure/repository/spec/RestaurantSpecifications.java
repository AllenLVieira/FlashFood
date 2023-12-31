package br.com.allen.flashfood.infrastructure.repository.spec;

import br.com.allen.flashfood.domain.model.Restaurant;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;

public class RestaurantSpecifications {
  private RestaurantSpecifications() {
    throw new IllegalStateException("Utility class");
  }

  public static Specification<Restaurant> withFreeShipping() {
    return (root, query, builder) -> builder.equal(root.get("freightRate"), BigDecimal.ZERO);
  }

  public static Specification<Restaurant> withSimilarName(String name) {
    return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
  }
}
