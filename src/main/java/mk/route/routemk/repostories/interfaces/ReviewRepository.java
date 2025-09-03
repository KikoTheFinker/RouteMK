package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends  GenericRepository<Review, Integer> {
    boolean existsByTicket_ticketId(Integer ticketId);
    List<Review> findByAccountEmail(String email);
}
