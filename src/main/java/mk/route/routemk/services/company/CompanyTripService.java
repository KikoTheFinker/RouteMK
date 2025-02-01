package mk.route.routemk.services.company;

import mk.route.routemk.models.Trip;
import mk.route.routemk.services.auth.AuthenticationService;
import mk.route.routemk.services.interfaces.TripService;
import mk.route.routemk.specifications.TripSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyTripService {
    private final TripService tripService;
    private final AuthenticationService authenticationService;

    public CompanyTripService(TripService tripService, AuthenticationService authenticationService) {
        this.tripService = tripService;
        this.authenticationService = authenticationService;
    }
    public List<Trip> getAuthorizedTripsByRoute(Integer routeId) {
        Integer transportOrganizerId = authenticationService.getTransportOrganizerId() != null
                ? authenticationService.getTransportOrganizerId()
                : authenticationService.getTransportOrganizerIdFromDriverAccountId();

        List<Trip> trips = tripService.findAllByPredicate(TripSpecification.tripsByRoute(routeId));

        if (!trips.isEmpty() && !trips.get(0).getTranOrg().getTranOrgId().equals(transportOrganizerId)) {
            throw new SecurityException("Unauthorized to access these trips.");
        }
        return trips;
    }
}
