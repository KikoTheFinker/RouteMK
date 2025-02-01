package mk.route.routemk.specifications;

import mk.route.routemk.models.TripStop;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class TripStopSpecification {
    public static Specification<TripStop> tripStopsByTripIds(List<Integer> tripIds) {
        return (root, query, criteriaBuilder) -> {
            if (tripIds == null || tripIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("trip").get("tripId").in(tripIds);
        };
    }
}
