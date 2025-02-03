package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Trip;

import java.util.Collection;
import java.util.List;

public interface TripService extends GenericService<Trip, Integer> {

    Collection<? extends Trip> findTripsBookedByAccount(Integer accountId);
    List<Trip> findTripsByRouteId(Integer routeId);
}
