package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestaurantRepository
        extends JpaRepository<Restaurant, Long>, RestaurantRepositoryQueries,
        JpaSpecificationExecutor<Restaurant> {
    List<Restaurant> consultByNameAndBetweenFee(String name, BigDecimal initialFee, BigDecimal finalFee);

    List<Restaurant> consultByNameAndCuisineId(String name, @Param("id") Long cuisineId);

    List<Restaurant> findByFreightRateBetween(BigDecimal initalFee, BigDecimal finalFee);

    int countByCuisineId(Long cuisineId);
}
