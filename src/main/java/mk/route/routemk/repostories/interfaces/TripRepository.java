package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Trip;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends GenericRepository<Trip, Integer>{
}
