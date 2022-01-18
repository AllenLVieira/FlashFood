package br.com.allen.flashfood.infrastructure.repository;

import br.com.allen.flashfood.domain.model.Restaurant;
import br.com.allen.flashfood.domain.repository.RestaurantRepository;
import br.com.allen.flashfood.domain.repository.RestaurantRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static br.com.allen.flashfood.infrastructure.repository.spec.RestaurantSpecifications.withFreeShipping;
import static br.com.allen.flashfood.infrastructure.repository.spec.RestaurantSpecifications.withSimilarName;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Lazy
    private RestaurantRepository restaurantRepository;

    /**
     * @param name
     * @return restaurants with free shipping and by name
     * obs: To avoid circular reference use the @Lazy annotation
     */
    @Override
    public List<Restaurant> consultFreeShippingAndName(String name) {
        return restaurantRepository.findAll(withFreeShipping()
                .and(withSimilarName(name)));
    }

    @Override
    public List<Restaurant> consultByNameAndBetweenFee(String name, BigDecimal initialFee,
                                                       BigDecimal finalFee) {
        var jpql = new StringBuilder();
        var parameters = new HashMap<String, Object>();
        jpql.append("from Restaurant where 0 = 0 ");
        if (StringUtils.hasLength(name)) {
            jpql.append("and name like :name ");
            parameters.put("name", "%" + name + "%");
        }
        if (initialFee != null) {
            jpql.append("and freightRate >= :initialFee ");
            parameters.put("initialFee", initialFee);
        }
        if (finalFee != null) {
            jpql.append("and freightRate <= :finalFee");
            parameters.put("finalFee", finalFee);
        }
        TypedQuery<Restaurant> query = entityManager.createQuery(jpql.toString(), Restaurant.class);
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }

    @Override
    public List<Restaurant> consultByNameAndBetweenFeeCriteria(String name, BigDecimal initialFee,
                                                               BigDecimal finalFee) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurant> criteria =
                builder.createQuery(Restaurant.class);
        Root<Restaurant> root = criteria.from(Restaurant.class);
        var predicates = new ArrayList<Predicate>();
        if (StringUtils.hasLength(name)) {
            predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        }
        if (initialFee != null) {
            predicates.add(builder
                    .greaterThanOrEqualTo(root.get("freightRate"), initialFee));
        }
        if (finalFee != null) {
            predicates.add(builder
                    .lessThanOrEqualTo(root.get("freightRate"), finalFee));
        }
        criteria.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Restaurant> query = entityManager.createQuery(criteria);
        return query.getResultList();
    }
}