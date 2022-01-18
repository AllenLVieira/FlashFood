package br.com.allen.flashfood.infrastructure.repository.spec;

import br.com.allen.flashfood.domain.model.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class RestaurantSimilarNameSpecification implements Specification<Restaurant> {
    private static final long serialVersionUID = 1L;
    private String name;

    @Override
    public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query,
                                 CriteriaBuilder builder) {
        return builder.like(root.get("name"), "%" + name + "%");
    }

}
