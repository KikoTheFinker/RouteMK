package mk.route.routemk.services.company;

import mk.route.routemk.models.Location;
import mk.route.routemk.models.Route;
import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.services.company.interfaces.CompanyAuthorizationService;
import mk.route.routemk.services.company.interfaces.CompanyRouteService;
import mk.route.routemk.services.interfaces.LocationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.specifications.RouteSpecification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CompanyRouteServiceImpl implements CompanyRouteService {
    private final RouteService routeService;
    private final CompanyAuthorizationService companyAuthorizationService;
    private final LocationService locationService;

    public CompanyRouteServiceImpl(RouteService routeService, CompanyAuthorizationServiceImpl companyAuthorizationService, LocationService locationService) {
        this.routeService = routeService;
        this.companyAuthorizationService = companyAuthorizationService;
        this.locationService = locationService;
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

    @Override
    public void createRouteForAuthenticatedOrganizer(Integer sourceLocationId, Integer destinationLocationId) {
        TransportOrganizer organizer = companyAuthorizationService.getAuthenticatedTransportOrganizer();
        if (organizer == null) throw new IllegalStateException("No authenticated organizer.");

        if (Objects.equals(sourceLocationId, destinationLocationId)) {
            throw new IllegalArgumentException("Source and destination must be different.");
        }

        Location source = locationService.findById(sourceLocationId);
        Location destination = locationService.findById(destinationLocationId);
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Invalid source or destination.");
        }
        boolean exists = !routeService.findAllByPredicate(
                RouteSpecification.byOrgAndEndpoints(organizer.getTranOrgId(), sourceLocationId, destinationLocationId)
        ).isEmpty();

        if (exists) {
            throw new IllegalStateException("This route already exists for the organizer.");
        }

        Route route = new Route(source, destination, organizer);
        routeService.save(route);
    }

}
