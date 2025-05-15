package com.tup.ps.erpevents.specifications;

import com.tup.ps.erpevents.entities.TemplateEntity;
import org.springframework.data.jpa.domain.Specification;


public class TemplateSpecifications {


    public static Specification<TemplateEntity> createSpecification(Boolean active, Boolean hasPlaceholders, String name) {
        return (root, query, criteriaBuilder) -> {
            var predicates = criteriaBuilder.conjunction();

            if (active != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("active"), active));
            }
            if (hasPlaceholders != null) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.equal(root.get("hasPlaceholders"), hasPlaceholders));
            }
            if (name != null && !name.isEmpty()) {
                predicates = criteriaBuilder.and(predicates, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            return predicates;
        };
    }

}
