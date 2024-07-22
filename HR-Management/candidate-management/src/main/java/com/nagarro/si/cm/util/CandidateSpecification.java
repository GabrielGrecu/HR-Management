package com.nagarro.si.cm.util;

import com.nagarro.si.cm.entity.Candidate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CandidateSpecification {

    public static Specification<Candidate> createDynamicSearchSpecification(Map<String, Object> filters) {
        return (Root<Candidate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = buildPredicates(filters, root, criteriaBuilder);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static List<Predicate> buildPredicates(Map<String, Object> filters, Root<Candidate> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : filters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            try {
                Field field = Candidate.class.getDeclaredField(key);
                if (Objects.nonNull(value)) {
                    predicates.add(createPredicate(field, key, value, root, criteriaBuilder));
                }
            } catch (NoSuchFieldException exception) {
                throw new IllegalArgumentException("Invalid filter name");
            }
        }
        return predicates;
    }

    private static Predicate createPredicate(Field field, String key, Object value, Root<Candidate> root, CriteriaBuilder criteriaBuilder) {
        if (field.getType().equals(String.class)) {
            String likePattern = "%" + value.toString().toLowerCase() + "%";
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(key)), likePattern
            );
        } else {
            return criteriaBuilder.equal(root.get(key), value);
        }
    }
}
