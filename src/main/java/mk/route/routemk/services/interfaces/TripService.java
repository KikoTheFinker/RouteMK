package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Trip;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TripService extends GenericService<Trip, Integer> {

    List<Trip> findTripsBookedByAccount(Integer accountId);

    List<Trip> findTripsByRouteId(Integer routeId);

    Integer numTicketsLeftForTrip(Integer tripId);

    Map<Integer, String> getStopNameTableForTrips(Collection<? extends Trip> trips, String from, String to);

    Map<Integer, Double> getCheapestTicketTableForTrips(Collection<? extends Trip> trips);

    Map<Integer, Integer> getFreeSeatTableForTrips(Collection<? extends Trip> trips);

    Optional<Trip> findCheapestTripForRoute(Integer routeId);

    Optional<Trip> findFastestTripForRoute(Integer routeId);

    Optional<Trip> findCheapestTripForRoute(Integer routeId, LocalDate date);

    Optional<Trip> findFastestTripForRoute(Integer routeId, LocalDate date);

}