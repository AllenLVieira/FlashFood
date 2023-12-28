package br.com.allen.flashfood.infrastructure.repository.spec;

import br.com.allen.flashfood.domain.model.DeliveryOrder;
import br.com.allen.flashfood.domain.repository.filter.DeliveryOrderFilter;
import java.util.ArrayList;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class DeliveryOrderSpecifications {
    private DeliveryOrderSpecifications() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<DeliveryOrder> usingFilters(DeliveryOrderFilter filter) {
        return (root, query, builder) -> {
            if (DeliveryOrder.class.equals(query.getResultType())) {
                root.fetch("restaurant").fetch("cuisine");
                root.fetch("user");
            }

            var predicates = new ArrayList<Predicate>();

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("user"), filter.getClientId()));
            }
            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }
            if (filter.getInitialRegistrationDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("registrationDate"),
                        filter.getInitialRegistrationDate()));
            }
            if (filter.getEndRegistrationDate() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("registrationDate"),
                        filter.getEndRegistrationDate()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
