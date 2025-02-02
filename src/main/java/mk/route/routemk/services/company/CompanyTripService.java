package mk.route.routemk.services.company;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TripService;
import mk.route.routemk.specifications.TripSpecification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class CompanyTripService {
    private final TripService tripService;
    private final CompanyAuthorizationService authorizationService;

    public CompanyTripService(TripService tripService, CompanyAuthorizationService authorizationService) {
        this.tripService = tripService;
        this.authorizationService = authorizationService;
    }

    /**
     * Returns every trip for specific route if user is authorized.
     * @param routeId the routeId for getting authorized trips
     * @return all trips for the route || SecurityException("Unauthorized to access these trips.")
     */
    public List<Trip> getAuthorizedTripsByRoute(Integer routeId) {
        Integer transportOrganizerId = authorizationService.getAuthenticatedTransportOrganizerId();

        List<Trip> trips = tripService.findAllByPredicate(TripSpecification.tripsByRoute(routeId));

        if (!trips.isEmpty() && !trips.get(0).getTranOrg().getTranOrgId().equals(transportOrganizerId)) {
            throw new SecurityException("Unauthorized to access these trips.");
        }
        return trips;
    }
    /**
     * Deletes a trip only if the authenticated transport organizer owns it.
     * @param tripId the trip to check upon
     * @return true if the trip is deleted else false.
     */
    public boolean deleteTripIfAuthorized(Integer tripId) {
        return authorizationService.isAuthorizedTransportOrganizer(tripService.findById(tripId).getRoute().getTranOrg().getTranOrgId())
                ? ((Supplier<Boolean>)(() -> { tripService.deleteById(tripId); return true; })).get()
                : false;
    }

}
