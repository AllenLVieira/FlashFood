package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.Restaurant;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository
    extends JpaRepository<Restaurant, Long>,
        RestaurantRepositoryQueries,
        JpaSpecificationExecutor<Restaurant> {

  /**
   * To improve query performance with fetch If you need to do the same with ManyToMany, can do a
   * LEFT JOIN FETCH
   */
  @Query("from Restaurant r join fetch r.cuisine")
  List<Restaurant> findAll();

  List<Restaurant> consultByNameAndBetweenFee(
      String name, BigDecimal initialFee, BigDecimal finalFee);

  List<Restaurant> consultByNameAndCuisineId(String name, @Param("id") Long cuisineId);

  List<Restaurant> findByFreightRateBetween(BigDecimal initalFee, BigDecimal finalFee);

  int countByCuisineId(Long cuisineId);
}
