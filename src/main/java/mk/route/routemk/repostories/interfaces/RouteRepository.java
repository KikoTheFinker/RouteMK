package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Route;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends GenericRepository<Route, Integer> {
}
