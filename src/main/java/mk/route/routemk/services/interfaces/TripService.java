package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Trip;

import java.util.List;

public interface TripService extends GenericService<Trip, Integer> {

    List<? extends Trip> findTripsBookedByAccount(Integer accountId);

}
