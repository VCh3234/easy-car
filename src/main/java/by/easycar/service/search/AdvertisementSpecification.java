package by.easycar.service.search;

import by.easycar.exceptions.SearchException;
import by.easycar.model.advertisement.Advertisement;
import by.easycar.model.advertisement.Vehicle;
import by.easycar.model.dto.SearchParams;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class AdvertisementSpecification implements Specification<Advertisement> {

    private final SearchParams searchParams;

    private final boolean moderationFlag;

    private final Set<String> whiteListOfAttributes = Set.of(
            "price",
            "region",
            "mileage",
            "engineCapacity",
            "engineType",
            "transmissionType",
            "carYear",
            "brand",
            "model",
            "generation",
            "bodyType"
    );

    public AdvertisementSpecification(SearchParams searchParams, boolean moderationFlag) {
        this.searchParams = searchParams;
        this.moderationFlag = moderationFlag;
    }

    @Override
    public Predicate toPredicate(Root<Advertisement> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if(moderationFlag) {
            return builder.equal(root.get("moderated"), true);
        }
        if (whiteListOfAttributes.contains(searchParams.getKey())) {
            if (searchParams.getEntity().equals("advertisement")) {
                return getPredicateForAdvertisement(root, builder);
            } else if (searchParams.getEntity().equals("vehicle")) {
                return getPredicateForVehicle(root, builder);
            } else {
                throw new SearchException("Unsupported entity value.");
            }
        } else {
            throw new SearchException("Unsupported key value.");
        }
    }

    private Predicate getPredicateForVehicle(Root<Advertisement> root, CriteriaBuilder builder) {
        if (searchParams.getOperation().equals(":")) {
            Join<Vehicle, Advertisement> join = root.join("vehicle");
            return builder.like(join.get(searchParams.getKey()), "%" + searchParams.getValue() + "%");
        } else {
            throw new SearchException("Unsupported operation type for vehicle.");
        }
    }

    private Predicate getPredicateForAdvertisement(Root<Advertisement> root, CriteriaBuilder builder) {
        switch (searchParams.getOperation()) {
            case ">" -> {
                if (this.getJavaType(root) == String.class) {
                    throw new SearchException("Unsupported operation for this key.");
                }
                return builder.greaterThanOrEqualTo(root.get(searchParams.getKey()), searchParams.getValue());
            }
            case "<" -> {
                if (this.getJavaType(root) == String.class) {
                    throw new SearchException("Unsupported operation for this key.");
                }
                return builder.lessThanOrEqualTo(root.get(searchParams.getKey()), searchParams.getValue());
            }
            case ":" -> {
                if (this.getJavaType(root) == String.class) {
                    return builder.like(root.get(searchParams.getKey()), "%" + searchParams.getValue() + "%");
                } else {
                    return builder.equal(root.get(searchParams.getKey()), searchParams.getValue());
                }
            }
            default -> throw new SearchException("Unsupported operation type.");
        }
    }

    private Class<?> getJavaType(Root<?> root) {
        return root.get(searchParams.getKey()).getJavaType();
    }
}