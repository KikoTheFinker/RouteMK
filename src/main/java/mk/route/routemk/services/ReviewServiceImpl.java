package mk.route.routemk.services;

import mk.route.routemk.models.Review;
import mk.route.routemk.models.Ticket;
import mk.route.routemk.models.Trip;
import mk.route.routemk.repostories.interfaces.ReviewRepository;
import mk.route.routemk.repostories.interfaces.TicketRepository;
import mk.route.routemk.repostories.interfaces.TripRepository;
import mk.route.routemk.services.interfaces.ReviewService;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ReviewServiceImpl implements ReviewService {


    private final ReviewRepository reviewRepository;
    private final TripRepository tripRepository;
    private final TicketRepository ticketRepository;


    public ReviewServiceImpl(ReviewRepository reviewRepository, TripRepository tripRepository, TicketRepository ticketRepository) {
        this.reviewRepository = reviewRepository;
        this.tripRepository = tripRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Review> findReviewsForTrip(Integer tripId) {

        List<Review> reviews = reviewRepository.findAll();
        List<Review> filteredReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getTicket().getTrip().getTripId() == tripId) {
                filteredReviews.add(review);
            }
        }
        return filteredReviews;
    }

    @Override
    public List<Review> findReviewsForRoute(Integer routeId) {
        List<Review> reviews = reviewRepository.findAll();
        List<Review> filteredReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (Objects.equals(review.getTicket().getTrip().getRoute().getRouteId(), routeId)) {
                filteredReviews.add(review);
            }
        }
        return filteredReviews;
    }


    @Override
    public List<Review> findReviewsForTransportOrganizer(Integer transportOrganizerId) {
        List<Review> reviews = reviewRepository.findAll();
        List<Review> filteredReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (Objects.equals(review.getTicket().getTrip().getRoute().getTranOrg().getTranOrgId(), transportOrganizerId)) {
                filteredReviews.add(review);
            }
        }
        return filteredReviews;
    }
}

