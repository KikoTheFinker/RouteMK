package mk.route.routemk.services;

import mk.route.routemk.models.Review;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.ReviewRepository;
import mk.route.routemk.repostories.interfaces.TicketRepository;
import mk.route.routemk.repostories.interfaces.TripRepository;
import mk.route.routemk.services.interfaces.ReviewService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mk.route.routemk.specifications.ReviewSpecification.*;

@Service
public class ReviewServiceImpl extends GenericServiceImpl<Review, Integer> implements ReviewService {


    private final ReviewRepository reviewRepository;
    private final TripRepository tripRepository;
    private final TicketRepository ticketRepository;


    public ReviewServiceImpl(GenericRepository<Review, Integer> genericRepository, ReviewRepository reviewRepository, TripRepository tripRepository, TicketRepository ticketRepository) {
        super(genericRepository);
        this.reviewRepository = reviewRepository;
        this.tripRepository = tripRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Review> findReviewsForTrip(Integer tripId) {
        return findAllByPredicate(hasTripId(tripId)).stream().toList();
    }

    @Override
    public List<Review> findReviewsForRoute(Integer routeId) {
        return findAllByPredicate(hasRouteId(routeId)).stream().toList();
    }


    @Override
    public List<Review> findReviewsForTransportOrganizer(Integer transportOrganizerId) {
        return findAllByPredicate(hasTransportOrganizerId(transportOrganizerId)).stream().toList();
    }
}

