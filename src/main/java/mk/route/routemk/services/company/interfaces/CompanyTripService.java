package mk.route.routemk.services.company.interfaces;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CompanyTripService {
    void updateTrip(Route route, Integer tripId, LocalDate date, int freeSeats, List<Integer> locationIds, List<LocalTime> etas);

    void createTrip(Route route, LocalDate date, int freeSeats, List<Integer> locationIds, List<LocalTime> etas);

    List<Trip> getAuthorizedTripsByRoute(Integer routeId);

    boolean deleteTripIfAuthorized(Integer tripId);
}
