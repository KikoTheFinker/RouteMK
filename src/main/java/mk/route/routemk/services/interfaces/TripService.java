package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Trip;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface TripService extends GenericService<Trip, Integer> {

    List<Trip> findTripsBookedByAccount(Integer accountId);
    List<Trip> findTripsByRouteId(Integer routeId);
    Integer numTicketsLeftForTrip(Integer tripId);
    Map<Integer, String> getStopNameTableForTrips(Collection<? extends Trip> trips, String from, String to);
    Map<Integer, Object> getCheapestTicketTableForTrips(Collection<? extends Trip> trips);
    Map<Integer, Integer> getFreeSeatTableForTrips(Collection<? extends Trip> trips);

}
