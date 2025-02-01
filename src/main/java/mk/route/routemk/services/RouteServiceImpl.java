package mk.route.routemk.services;

import mk.route.routemk.models.Route;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.RouteService;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl extends GenericServiceImpl<Route, Integer> implements RouteService {
    public RouteServiceImpl(GenericRepository<Route, Integer> genericRepository) {
        super(genericRepository);
    }
}
