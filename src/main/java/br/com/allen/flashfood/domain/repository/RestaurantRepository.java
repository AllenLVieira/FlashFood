package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("from Restaurant where name like %:name% and cuisine.id = :id")
    List<Restaurant> findByNameAndCuisineId(String name, @Param("id") Long cuisineId);

    List<Restaurant> findByFreightRateBetween(BigDecimal initalFee, BigDecimal finalFee);

    int countByCuisineId(Long cuisineId);
}
