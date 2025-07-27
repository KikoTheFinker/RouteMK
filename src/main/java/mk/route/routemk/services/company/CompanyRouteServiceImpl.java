package mk.route.routemk.services.company;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.services.company.interfaces.CompanyAuthorizationService;
import mk.route.routemk.services.company.interfaces.CompanyRouteService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.specifications.RouteSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyRouteServiceImpl implements CompanyRouteService {
    private final RouteService routeService;
    private final CompanyAuthorizationService companyAuthorizationService;

    public CompanyRouteServiceImpl(RouteService routeService, CompanyAuthorizationServiceImpl companyAuthorizationService) {
        this.routeService = routeService;
        this.companyAuthorizationService = companyAuthorizationService;
    }

    /**
     * Fetches routes only for the company that the user works
     *
     * @return routes for the company that the user works
     */
    public List<Route> getAuthorizedRoutes() {
        Integer transportOrganizerId = companyAuthorizationService.getAuthenticatedTransportOrganizerId();
        return routeService.findAllByPredicate(RouteSpecification.routesByTransportOrganizer(transportOrganizerId));
    }

    /**
     * Deletes a route only if the authenticated transport organizer owns it.
     *
     * @param routeId the route to check upon
     * @return true if the route is deleted else false.
     */
    public boolean deleteRouteIfAuthorized(Integer routeId) {
        if (companyAuthorizationService.isAuthorizedTransportOrganizer(routeService.findById(routeId).getTranOrg().getTranOrgId())) {
            routeService.deleteById(routeId);
            return true;
        }
        return false;
    }
}
