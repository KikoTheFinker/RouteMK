package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Review;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends GenericRepository<Review, Integer> {
}
