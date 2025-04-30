package com.tup.ps.erpevents.repositories.specs;

import jakarta.persistence.criteria.Predicate;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

@NoArgsConstructor
@Component
public class GenericSpecification<T> {

    public Specification<T> filterByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                return criteriaBuilder.between(root.get("createdDate"), startDate, endDate);
            } else if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), startDate);
            } else if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), endDate);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public <E extends Comparable<? super E>> Specification<T> filterBetween(E lower, E higher, String field) {
        return (root, query, criteriaBuilder) -> {
            E finalLower = lower;
            E finalHigher = higher;

            if (finalLower != null && finalHigher != null && finalHigher.compareTo(finalLower) < 0) {
                E temp = finalLower;
                finalLower = finalHigher;
                finalHigher = temp;
            }

            if (finalLower != null && finalHigher != null) {
                return criteriaBuilder.between(root.get(field), finalLower, finalHigher);
            } else if (finalLower != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(field), finalLower);
            } else if (finalHigher != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get(field), finalHigher);
            }

            return criteriaBuilder.conjunction();
        };
    }


    public Specification<T> dynamicFilter(Map<String, Object> attributtes) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            for (Map.Entry<String, Object> entry : attributtes.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value != null) {
                    predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get(key), value));
                }
            }

            return predicates;
        };
    }

    public Specification<T> valueDynamicFilter(String value, String... entityFields) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicates = criteriaBuilder.conjunction();

            if (value != null) {
                String likePattern = "%" + value.toLowerCase(Locale.ROOT) + "%";
                Predicate orPredicates = criteriaBuilder.disjunction();

                for (String field : entityFields) {
                    orPredicates = criteriaBuilder.or(
                            orPredicates,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get(field).as(String.class)), likePattern)
                    );
                }

                predicates = criteriaBuilder.and(predicates, orPredicates);
            }

            return predicates;
        };
    }
}
