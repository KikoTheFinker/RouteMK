package mk.route.routemk.specifications;

import mk.route.routemk.models.Route;
import org.springframework.data.jpa.domain.Specification;

public class RouteSpecification {

    public static Specification<Route>  routesByTransportOrganizer(Integer transportOrganizerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("tranOrg").get("tranOrgId"), transportOrganizerId);
    }

}
