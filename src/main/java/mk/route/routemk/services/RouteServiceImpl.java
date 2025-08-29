package mk.route.routemk.services;

import mk.route.routemk.models.Route;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.specifications.RouteSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl extends GenericServiceImpl<Route, Integer> implements RouteService {

    public RouteServiceImpl(GenericRepository<Route, Integer> genericRepository) {
        super(genericRepository);
    }

    public List<Route> findRouteByFromAndToDest(String from, String to) {
        return findAllByPredicate(RouteSpecification.routesByFromAndToLocation(from, to));
    }

}
