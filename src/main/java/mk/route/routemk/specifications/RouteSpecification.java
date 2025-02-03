package mk.route.routemk.specifications;

import mk.route.routemk.models.Route;
import org.springframework.data.jpa.domain.Specification;

public class RouteSpecification {

    public static Specification<Route> routesByTransportOrganizer(Integer transportOrganizerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tranOrg").get("tranOrgId"), transportOrganizerId);
    }

    public static Specification<Route> routesByFromAndToLocation(String fromLocation, String toLocation) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("source").get("name")), "%" + fromLocation.toLowerCase() + "%"),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("destination").get("name")), "%" + toLocation.toLowerCase() + "%")
                );
    }


}
