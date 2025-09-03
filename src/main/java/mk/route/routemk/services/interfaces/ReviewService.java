package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Review;
import mk.route.routemk.models.Trip;

import java.util.List;

public interface ReviewService {
    List<Review> findReviewsForTrip(Integer tripId);

    List<Review> findReviewsForRoute(Integer routeId);

    void addReview(Integer tripId, Integer accountId, String description, Integer rating);

    void deleteById(Integer reviewId);
    List<Review> findByAccountEmail(String email);
}
