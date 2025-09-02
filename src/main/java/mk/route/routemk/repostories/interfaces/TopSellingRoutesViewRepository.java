package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.views.TopSellingRouteView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopSellingRoutesViewRepository extends GenericRepository<TopSellingRouteView, String> {
    List<TopSellingRouteView> getAllByTransportOrganizerName(String transportOrganizerName);
}
