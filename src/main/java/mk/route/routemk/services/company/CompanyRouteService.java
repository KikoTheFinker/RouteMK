package mk.route.routemk.services.company;

import mk.route.routemk.models.Route;
import mk.route.routemk.services.auth.AuthenticationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.specifications.RouteSpecification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class CompanyRouteService {
    private final RouteService routeService;
    private final CompanyAuthorizationService authorizationService;

    public CompanyRouteService(RouteService routeService,  CompanyAuthorizationService authorizationService) {
        this.routeService = routeService;
        this.authorizationService = authorizationService;
    }

    /**
     * Fetches routes only for the company that the user works
     * @return routes for the company that the user works
     */
    public List<Route> getAuthorizedRoutes() {
        Integer transportOrganizerId = authorizationService.getAuthenticatedTransportOrganizerId();
        return routeService.findAllByPredicate(RouteSpecification.routesByTransportOrganizer(transportOrganizerId));
    }

    /**
     * Deletes a route only if the authenticated transport organizer owns it.
     * @param routeId the route to check upon
     * @return true if the route is deleted else false.
     */
    public boolean deleteRouteIfAuthorized(Integer routeId) {
        return authorizationService.isAuthorizedTransportOrganizer(routeService.findById(routeId).getTranOrg().getTranOrgId())
                ? ((Supplier<Boolean>)(() -> { routeService.deleteById(routeId); return true; })).get()
                : false;
    }
}
