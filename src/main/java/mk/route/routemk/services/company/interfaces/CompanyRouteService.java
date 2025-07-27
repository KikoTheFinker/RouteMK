package mk.route.routemk.services.company.interfaces;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.TransportOrganizer;

import java.util.List;

public interface CompanyRouteService {
    List<Route> getAuthorizedRoutes();
    boolean deleteRouteIfAuthorized(Integer routeId);
}
