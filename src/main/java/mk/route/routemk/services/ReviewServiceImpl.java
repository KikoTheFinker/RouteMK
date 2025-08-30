package mk.route.routemk.services;

import mk.route.routemk.models.Review;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.ReviewRepository;
import mk.route.routemk.services.interfaces.ReviewService;
import org.springframework.stereotype.Service;
import java.util.List;

import static mk.route.routemk.specifications.ReviewSpecification.*;

@Service
public class ReviewServiceImpl extends GenericServiceImpl<Review, Integer> implements ReviewService {

    private final ReviewRepository reviewRepository;
    public ReviewServiceImpl(GenericRepository<Review, Integer> genericRepository, ReviewRepository reviewRepository) {
        super(genericRepository);
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findReviewsForTrip(Integer tripId) {
        System.out.println(reviewRepository.findAll());
        return findAllByPredicate(hasTripId(tripId)).stream().toList();
    }

    @Override
    public List<Review> findReviewsForRoute(Integer routeId) {
        return findAllByPredicate(hasRouteId(routeId)).stream().toList();
    }

}

