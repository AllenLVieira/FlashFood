package br.com.allen.flashfood.domain.repository;

import br.com.allen.flashfood.domain.model.Product;
import br.com.allen.flashfood.domain.model.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("from Product where restaurant.id = :restaurant and id = :product")
  Optional<Product> findById(
      @Param("restaurant") Long restaurantId, @Param("product") Long productId);

  List<Product> findByRestaurant(Restaurant restaurant);

  @Query("from Product p where p.restaurant = :restaurant and p.active = true")
  List<Product> findActiveProductsByRestaurant(Restaurant restaurant);
}
