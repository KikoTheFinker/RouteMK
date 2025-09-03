package mk.route.routemk.specifications;

import jakarta.persistence.criteria.Predicate;
import mk.route.routemk.models.Route;
import org.springframework.data.jpa.domain.Specification;

public class RouteSpecification {

    public static Specification<Route> routesByTransportOrganizer(Integer transportOrganizerId) {
        return (root, query, cb) ->
                cb.equal(root.get("tranOrg").get("tranOrgId"), transportOrganizerId);
    }

    public static Specification<Route> routesByFromAndToLocation(String fromLocation, String toLocation) {
        final String from = (fromLocation == null) ? "" : fromLocation.trim();
        final String to   = (toLocation == null)   ? "" : toLocation.trim();

        return (root, query, cb) -> {
            java.util.List<Predicate> predicates = new java.util.ArrayList<>();

            if (!from.isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("source").get("name")),
                        "%" + from.toLowerCase() + "%"
                ));
            }
            if (!to.isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("destination").get("name")),
                        "%" + to.toLowerCase() + "%"
                ));
            }

            return predicates.isEmpty() ? cb.conjunction() : cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<Route> byOrgAndEndpoints(Integer orgId, Integer sourceId, Integer destId) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("tranOrg").get("tranOrgId"), orgId),
                cb.equal(root.get("source").get("id"), sourceId),
                cb.equal(root.get("destination").get("id"), destId)
        );
    }
}
