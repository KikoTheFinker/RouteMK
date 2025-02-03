package mk.route.routemk.services.company;

import mk.route.routemk.models.Trip;
import mk.route.routemk.models.TripStop;
import mk.route.routemk.models.enums.Status;
import mk.route.routemk.services.interfaces.TripService;
import mk.route.routemk.services.interfaces.TripStopService;
import mk.route.routemk.specifications.TripSpecification;
import mk.route.routemk.validators.TripValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Supplier;

@Service
public class CompanyTripService {
    private final TripService tripService;
    private final TripStopService tripStopService;
    private final CompanyAuthorizationService authorizationService;
    private final TripValidator tripValidator;

    public CompanyTripService(TripService tripService, TripStopService tripStopService, CompanyAuthorizationService authorizationService, TripValidator tripValidator) {
        this.tripService = tripService;
        this.tripStopService = tripStopService;
        this.authorizationService = authorizationService;
        this.tripValidator = tripValidator;
    }


    /**
     * Creates a new trip after validating input and checking authorization.
     */
    public void createTrip(Integer routeId, LocalDate date, int freeSeats, List<Integer> locationIds, List<LocalTime> etas) {
        tripValidator.validateTripData(freeSeats, locationIds, etas);
        Integer transportOrganizerId = authorizationService.getAuthenticatedTransportOrganizerId();

        if (!authorizationService.isAuthorizedTransportOrganizer(transportOrganizerId)) {
            throw new SecurityException("Unauthorized to access these trips.");
        }

        Trip trip = new Trip();
        trip.setDate(date);
        trip.setFreeSeats(freeSeats);
        trip.setTranOrgId(transportOrganizerId);
        trip.setRouteId(routeId);
        trip.setStatus(Status.NOT_STARTED);
        tripService.save(trip);

        saveTripStopsForTrip(trip.getTripId(), locationIds, etas);
    }


    /**
     * Saves trip stops associated with a trip.
     *
     * @param tripId      the trip id for saving the trip stops.
     * @param locationIds locations that the trip stops.
     * @param etas        the time of the locations that the trip stops.
     */
    private void saveTripStopsForTrip(int tripId, List<Integer> locationIds, List<LocalTime> etas) {
        for (int i = 0; i < locationIds.size(); i++) {
            TripStop tripStop = new TripStop();
            tripStop.setTripId(tripId);
            tripStop.setLocationId(locationIds.get(i));
            tripStop.setStopTime(etas.get(i));
            tripStopService.save(tripStop);
        }
    }

    /**
     * Returns every trip for specific route if user is authorized.
     *
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
     *
     * @param tripId the trip to check upon
     * @return true if the trip is deleted else false.
     */
    public boolean deleteTripIfAuthorized(Integer tripId) {
        return authorizationService.isAuthorizedTransportOrganizer(tripService.findById(tripId).getRoute().getTranOrg().getTranOrgId())
                ? ((Supplier<Boolean>) (() -> {
            tripService.deleteById(tripId);
            return true;})).get() : false;
    }

}
