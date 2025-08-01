package mk.route.routemk.services.company.interfaces;

import mk.route.routemk.models.Route;
import java.util.List;

public interface CompanyRouteService {
    List<Route> getAuthorizedRoutes();
    boolean deleteRouteIfAuthorized(Integer routeId);
}
