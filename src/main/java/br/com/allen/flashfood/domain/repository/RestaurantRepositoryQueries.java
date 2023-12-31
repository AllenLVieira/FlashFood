package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.Restaurant;
import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {
  List<Restaurant> consultFreeShippingAndName(String name);

  List<Restaurant> consultByNameAndBetweenFee(
      String name, BigDecimal initialFee, BigDecimal finalFee);

  List<Restaurant> consultByNameAndBetweenFeeCriteria(
      String name, BigDecimal initialFee, BigDecimal finalFee);
}
