package mk.route.routemk.specifications;

import mk.route.routemk.models.Trip;
import org.springframework.data.jpa.domain.Specification;

public class TripSpecification {
    public static Specification<Trip> tripsByRoute(Integer routeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("route").get("routeId"), routeId);
    }
}
