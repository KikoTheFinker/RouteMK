package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Review;

public interface ReviewRepository extends  GenericRepository<Review, Integer> {
    boolean existsByTicket_ticketId(Integer ticketId);
}
