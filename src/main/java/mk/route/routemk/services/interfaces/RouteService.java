package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Route;

import java.util.List;

public interface RouteService extends GenericService<Route, Integer> {

    List<Route> findRouteByFromAndToDest(String from, String to);
    List<Route> findIndirectRoutes(Integer from, Integer to);

}
