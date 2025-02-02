package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Trip;

import java.util.Collection;

public interface TripService extends GenericService<Trip, Integer> {

    Collection<? extends Trip> findTripsBookedByAccount(Integer accountId);

}
