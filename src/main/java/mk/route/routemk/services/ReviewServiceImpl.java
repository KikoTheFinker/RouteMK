package mk.route.routemk.services;

import mk.route.routemk.exceptions.ReviewRelatedException;
import mk.route.routemk.models.Account;
import mk.route.routemk.models.Review;
import mk.route.routemk.models.Ticket;
import mk.route.routemk.repostories.interfaces.*;
import mk.route.routemk.services.interfaces.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

import static mk.route.routemk.specifications.ReviewSpecification.*;

@Service
public class ReviewServiceImpl extends GenericServiceImpl<Review, Integer> implements ReviewService {

    private final AccountRepository accountRepository;
    private final TicketRepository ticketRepository;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(GenericRepository<Review, Integer> genericRepository, AccountRepository accountRepository, TicketRepository ticketRepository, ReviewRepository reviewRepository) {
        super(genericRepository);
        this.accountRepository = accountRepository;
        this.ticketRepository = ticketRepository;
        this.reviewRepository = reviewRepository;
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
    public void deleteById(Integer reviewId) {
        this.reviewRepository.deleteById(reviewId);
    }

    @Override
    public void addReview(Integer tripId, Integer accountId, String description, Integer rating) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<Ticket> tickets = ticketRepository.findTicketsByAccount_AccountIdAndTrip_TripId(accountId, tripId);
        if (tickets.isEmpty()) {
            throw new ReviewRelatedException("No ticket found for this account and trip, you have not been on this trip.");
        }
        Ticket ticket = tickets.get(0);

        if (reviewRepository.existsByTicket_ticketId(ticket.getTicketId())) {
           throw new ReviewRelatedException("You have already left a review for this trip.");
        };

        Review review = new Review();
        review.setAccount(account);
        review.setTicket(ticket);
        review.setDescription(description);
        review.setRating(rating);

        reviewRepository.save(review);
    }

}

