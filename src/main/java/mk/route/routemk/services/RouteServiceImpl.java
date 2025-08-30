package mk.route.routemk.services;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.TripRepository;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.specifications.RouteSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static mk.route.routemk.specifications.TripSpecification.findTripsWithStartAndEndLocations;

@Service
public class RouteServiceImpl extends GenericServiceImpl<Route, Integer> implements RouteService {

    private final TripRepository tripRepository;

    public RouteServiceImpl(GenericRepository<Route, Integer> genericRepository, TripRepository tripRepository) {
        super(genericRepository);
        this.tripRepository = tripRepository;
    }

    public List<Route> findRouteByFromAndToDest(String from, String to) {
        return findAllByPredicate(RouteSpecification.routesByFromAndToLocation(from, to));
    }

    @Override
    public List<Route> findIndirectRoutes(Integer from, Integer to) {
        Specification<Trip> indirectTrips = findTripsWithStartAndEndLocations(from, to);

        List<Trip> trips = tripRepository.findAll(indirectTrips);

        return trips.stream()
                .map(Trip::getRoute)
                .distinct()
                .toList();
    }

}
