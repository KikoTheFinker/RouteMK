package mk.route.routemk.services.company;

import jakarta.transaction.Transactional;
import mk.route.routemk.models.Driver;
import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.models.TripStop;
import mk.route.routemk.services.company.interfaces.CompanyAuthorizationService;
import mk.route.routemk.services.company.interfaces.CompanyTripService;
import mk.route.routemk.services.interfaces.DriverService;
import mk.route.routemk.services.interfaces.TripService;
import mk.route.routemk.services.interfaces.TripStopService;
import mk.route.routemk.specifications.TripSpecification;
import mk.route.routemk.validators.TripValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CompanyTripServiceImpl implements CompanyTripService {
    private final TripService tripService;
    private final TripStopService tripStopService;
    private final CompanyAuthorizationService authorizationService;
    private final DriverService driverService;
    private final TripValidator tripValidator;

    public CompanyTripServiceImpl(TripService tripService, TripStopService tripStopService, CompanyAuthorizationServiceImpl authorizationService, DriverService driverService, TripValidator tripValidator) {
        this.tripService = tripService;
        this.tripStopService = tripStopService;
        this.authorizationService = authorizationService;
        this.driverService = driverService;
        this.tripValidator = tripValidator;
    }

    /**
     * Creates a new trip after validating input and checking authorization.
     *
     * @param route       The route on which the trip being updated is undertaken.
     * @param tripId      The ID of the trip being updated.
     * @param freeSeats   Number of free initial/current seats on the trip.
     * @param locationIds Location ids (where the trip stops overall).
     * @param etas        ETAs (Estimated Times of Arrival) according to each location.
     */
    @Transactional
    public void updateTrip(Route route, Integer tripId, LocalDate date, int freeSeats, double basePrice,
                           List<Integer> locationIds, List<LocalTime> etas) throws IllegalArgumentException {
        validateAndAuthorizeTrip(route, freeSeats, locationIds, etas);
        if (basePrice < 0) throw new IllegalArgumentException("Base price must be >= 0.");

        Trip trip = tripService.findById(tripId);
        if (trip == null) {
            throw new IllegalArgumentException(String.format("Trip not found with ID: %d", tripId));
        }

        trip.setDate(date);
        trip.setFreeSeats(freeSeats);
        trip.setBasePrice(basePrice);
        tripService.save(trip);

        tripStopService.deleteByTripId(tripId);
        saveTripStopsForTrip(tripId, locationIds, etas);
    }


    public void createTrip(Route route, LocalDate date, int freeSeats, double basePrice,
                           List<Integer> locationIds, List<LocalTime> etas) throws IllegalArgumentException {
        validateAndAuthorizeTrip(route, freeSeats, locationIds, etas);
        if (basePrice < 0) throw new IllegalArgumentException("Base price must be >= 0.");

        Trip trip = new Trip();
        trip.setDate(date);
        trip.setFreeSeats(freeSeats);
        trip.setBasePrice(basePrice);
        trip.setTranOrgId(authorizationService.getAuthenticatedTransportOrganizerId());
        trip.setRouteId(route.getRouteId());
        tripService.save(trip);

        saveTripStopsForTrip(trip.getTripId(), locationIds, etas);
    }

    /**
     * Performs a check on the integrity of the trip being added/edited and as a safety measure checks if the current
     * user is a transport organizer.
     *
     * @param route       Route of the new/edited trip.
     * @param freeSeats   Number of free initial/current seats on the trip.
     * @param locationIds Location ids (where the trip stops overall).
     * @param etas        ETAs (Estimated Times of Arrival) according to each location.
     */
    private void validateAndAuthorizeTrip(Route route, int freeSeats, List<Integer> locationIds, List<LocalTime> etas) throws IllegalArgumentException {

        tripValidator.validateTripData(route.getSource().getId(), route.getDestination().getId(), freeSeats, locationIds, etas);
        Integer transportOrganizerId = authorizationService.getAuthenticatedTransportOrganizerId();

        if (!authorizationService.isAuthorizedTransportOrganizer(transportOrganizerId)) {
            throw new SecurityException("Unauthorized to access these trips.");
        }
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
        if (authorizationService.isAuthorizedTransportOrganizer
                (tripService.findById(tripId).getRoute().getTranOrg().getTranOrgId())
        ) {
            tripService.deleteById(tripId);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void assignDriverIfAuthorized(Integer tripId, Integer driverId) {
        Trip trip = tripService.findById(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found");
        }

        Integer authOrgId = authorizationService.getAuthenticatedTransportOrganizerId();
        if (!trip.getTranOrg().getTranOrgId().equals(authOrgId)) {
            throw new SecurityException("Unauthorized to assign driver to this trip.");
        }

        Driver driver = driverService.findById(driverId);
        if (driver == null) {
            throw new IllegalArgumentException("Driver not found");
        }
        if (!driver.getTranOrg().getTranOrgId().equals(authOrgId)) {
            throw new SecurityException("Driver does not belong to your company.");
        }

        boolean alreadyAssigned = trip.getDrivers().stream()
                .anyMatch(d -> d.getDriverId().equals(driverId));
        if (!alreadyAssigned) {
            trip.getDrivers().add(driver);
            tripService.save(trip);
        }
    }

    @Transactional
    @Override
    public void unassignDriverIfAuthorized(Integer tripId, Integer driverId) {
        Trip trip = tripService.findById(tripId);
        if (trip == null) {
            throw new IllegalArgumentException("Trip not found");
        }

        Integer authOrgId = authorizationService.getAuthenticatedTransportOrganizerId();
        if (!trip.getTranOrg().getTranOrgId().equals(authOrgId)) {
            throw new SecurityException("Unauthorized to unassign driver from this trip.");
        }

        trip.getDrivers().removeIf(d -> d.getDriverId().equals(driverId));
        tripService.save(trip);
    }
}
