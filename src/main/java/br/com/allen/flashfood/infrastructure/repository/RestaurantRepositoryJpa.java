package br.com.allen.flashfood.infrastructure.repository;

import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RestaurantRepositoryJpa implements RestaurantRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurant> getAllRestaurants() {
        return entityManager.createQuery("from Restaurant", Restaurant.class)
                .getResultList();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return entityManager.find(Restaurant.class, id);
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return entityManager.merge(restaurant);
    }

    @Override
    public void deleteRestaurant(Restaurant restaurant) {
        restaurant = getRestaurantById(restaurant.getId());
        entityManager.remove(restaurant);
    }
}
