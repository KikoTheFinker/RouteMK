package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Location;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends GenericRepository<Location, Integer> {
}
